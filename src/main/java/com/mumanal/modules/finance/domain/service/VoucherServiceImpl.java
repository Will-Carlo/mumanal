package com.mumanal.modules.finance.domain.service;

import com.mumanal.modules.finance.domain.dto.request.CreateVoucherRequest;
import com.mumanal.modules.finance.domain.dto.request.UpdateVoucherRequest;
import com.mumanal.modules.finance.domain.dto.response.VoucherResponse;
import com.mumanal.modules.finance.domain.repository.AffiliateRepository;
import com.mumanal.modules.finance.domain.repository.BankRepository;
import com.mumanal.modules.finance.domain.repository.VoucherRepository;
import com.mumanal.modules.finance.persistence.entity.FinAffiliateEntity;
import com.mumanal.modules.finance.persistence.entity.FinBankEntity;
import com.mumanal.modules.finance.persistence.entity.FinVoucherEntity;
import com.mumanal.modules.finance.persistence.mapper.VoucherMapper;
import com.mumanal.modules.generic.domain.repository.PersonRepository;
import com.mumanal.modules.generic.persistence.entity.GenPersonEntity;
import com.mumanal.shared.domain.exception.ResourceNotFoundException;
import com.mumanal.shared.domain.exception.ResourceAlreadyExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final BankRepository bankRepository;       // Inyección de repo Banco
    private final PersonRepository personRepository;   // Inyección de repo Persona
    private final VoucherMapper voucherMapper;
    private final AffiliateRepository affiliateRepository;
    private final String RESOURCE = "Voucher";

    public VoucherServiceImpl(VoucherRepository voucherRepository,
                              BankRepository bankRepository,
                              PersonRepository personRepository,
                              VoucherMapper voucherMapper, AffiliateRepository affiliateRepository) {
        this.voucherRepository = voucherRepository;
        this.bankRepository = bankRepository;
        this.personRepository = personRepository;
        this.voucherMapper = voucherMapper;
        this.affiliateRepository = affiliateRepository;
    }

    @Override
    public List<VoucherResponse> getAll() {
        return voucherMapper.toDto(voucherRepository.findAll());
    }

    @Override
    public VoucherResponse getById(Integer id) {
        FinVoucherEntity entity = voucherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));
        return voucherMapper.toDto(entity);
    }

    @Override
    @Transactional
    public VoucherResponse create(CreateVoucherRequest request) {
        FinVoucherEntity voucher = new FinVoucherEntity();

        // 1. Resolver BANCO (Buscar o Crear)
        FinBankEntity bankEntity = resolveBank(request.bank());
        voucher.setBank(bankEntity);

        // 2. Resolver PERSONA (Buscar o Crear)
        FinAffiliateEntity affiliateEntity = resolveAffiliate(request.affiliate());
        voucher.setAffiliate(affiliateEntity);

        // 3. Validar duplicidad de voucher (Mismo banco, mismo nro deposito)
        if (voucherRepository.existsByDepositNumberAndBank(request.depositNumber(), bankEntity.getId())) {
            // Opcional: lanzar excepción o advertencia
            throw new ResourceAlreadyExistsException(RESOURCE, "depositNumber", request.depositNumber().toString());
        }

        // 4. Setear datos directos
        voucher.setDepositNumber(request.depositNumber());
        voucher.setDepositDate(request.depositDate());
        voucher.setAmount(request.amount());
        voucher.setPeriod(request.period());
        voucher.setRegistrationDate(LocalDateTime.now()); // Fecha de registro actual

        return voucherMapper.toDto(voucherRepository.save(voucher));
    }

    @Override
    @Transactional
    public VoucherResponse update(Integer id, UpdateVoucherRequest request) {
        FinVoucherEntity entity = voucherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));

        // Actualizar relaciones solo si vienen IDs nuevos
        if (request.bankId() != null) {
            FinBankEntity bank = bankRepository.findById(request.bankId())
                    .orElseThrow(() -> new ResourceNotFoundException("Bank", "id", request.bankId().toString()));
            entity.setBank(bank);
        }

        if (request.personId() != null) {
            FinAffiliateEntity affiliate = affiliateRepository.findById(request.personId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person", "id", request.personId().toString()));
            entity.setAffiliate(affiliate);
        }

        voucherMapper.updateEntityFromDto(request, entity);
        return voucherMapper.toDto(voucherRepository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        FinVoucherEntity entity = voucherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));
        entity.softDelete();
        voucherRepository.save(entity);
    }

    // --- MÉTODOS PRIVADOS DE RESOLUCIÓN ---

    private FinBankEntity resolveBank(CreateVoucherRequest.BankReferenceDto dto) {
        if (dto.id() != null) {
            return bankRepository.findById(dto.id())
                    .orElseThrow(() -> new ResourceNotFoundException("Bank", "id", dto.id().toString()));
        }

        // Lógica de Crear Nuevo Banco
        // Primero verificamos si ya existe por código o nombre para no duplicar por error
        if (bankRepository.existsByBankCode(dto.bankCode())) {
            // Podríamos retornarlo si existe, pero por seguridad lanzamos excepción o lo buscamos
            throw new ResourceAlreadyExistsException("Bank", "code", dto.bankCode());
        }

        FinBankEntity newBank = new FinBankEntity();
        newBank.setName(dto.name());
        newBank.setBankCode(dto.bankCode());
        return bankRepository.save(newBank);
    }

    private FinAffiliateEntity resolveAffiliate(CreateVoucherRequest.AffiliateReferenceDto dto) {
        if (dto.id() != null) {
            return affiliateRepository.findById(dto.id())
                    .orElseThrow(() -> new ResourceNotFoundException("Affiliate", "id", dto.id().toString()));
        }

        // Lógica de Crear Nueva Persona
        // Verificar si existe por CI antes de crear
        if (dto.identityCard() != null && affiliateRepository.findByPersonIdentityCard(dto.identityCard()).isPresent()) {
            // Si existe por CI, lo usamos (autocompletado inteligente)
            return affiliateRepository.findByPersonIdentityCard(dto.identityCard()).get();
        }

        GenPersonEntity newPerson = new GenPersonEntity();
        newPerson.setFirstName(dto.firstName());
        newPerson.setSecondName(dto.secondName());
        newPerson.setPaternalSurname(dto.paternalSurname());
        newPerson.setMaternalSurname(dto.maternalSurname());
        newPerson.setIdentityCard(dto.identityCard());

        newPerson = personRepository.save(newPerson);

        FinAffiliateEntity newAffiliate = new FinAffiliateEntity();
        newAffiliate.setPerson(newPerson);

        return affiliateRepository.save(newAffiliate);
    }
}
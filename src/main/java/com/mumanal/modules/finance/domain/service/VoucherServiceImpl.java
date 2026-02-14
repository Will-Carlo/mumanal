package com.mumanal.modules.finance.domain.service;

import com.mumanal.modules.finance.domain.dto.request.CreateVoucherRequest;
import com.mumanal.modules.finance.domain.dto.request.UpdateVoucherRequest;
import com.mumanal.modules.finance.domain.dto.response.VoucherResponse;
import com.mumanal.modules.finance.domain.repository.BankRepository;
import com.mumanal.modules.finance.domain.repository.VoucherRepository;
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
    private final String RESOURCE = "Voucher";

    public VoucherServiceImpl(VoucherRepository voucherRepository,
                              BankRepository bankRepository,
                              PersonRepository personRepository,
                              VoucherMapper voucherMapper) {
        this.voucherRepository = voucherRepository;
        this.bankRepository = bankRepository;
        this.personRepository = personRepository;
        this.voucherMapper = voucherMapper;
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
        GenPersonEntity personEntity = resolvePerson(request.person());
        voucher.setPerson(personEntity);

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
            GenPersonEntity person = personRepository.findById(request.personId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person", "id", request.personId().toString()));
            entity.setPerson(person);
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

    private GenPersonEntity resolvePerson(CreateVoucherRequest.PersonReferenceDto dto) {
        if (dto.id() != null) {
            return personRepository.findById(dto.id())
                    .orElseThrow(() -> new ResourceNotFoundException("Person", "id", dto.id().toString()));
        }

        // Lógica de Crear Nueva Persona
        // Verificar si existe por CI antes de crear
        if (dto.identityCard() != null && personRepository.findByIdentityCard(dto.identityCard()).isPresent()) {
            // Si existe por CI, lo usamos (autocompletado inteligente)
            return personRepository.findByIdentityCard(dto.identityCard()).get();
        }

        GenPersonEntity newPerson = new GenPersonEntity();
        newPerson.setFirstName(dto.firstName());
        newPerson.setPaternalSurname(dto.paternalSurname());
        newPerson.setMaternalSurname(dto.maternalSurname());
        newPerson.setIdentityCard(dto.identityCard());

        return personRepository.save(newPerson);
    }
}
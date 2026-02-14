package com.mumanal.modules.finance.domain.service;

import com.mumanal.modules.finance.domain.dto.request.CreateAffiliateRequest;
import com.mumanal.modules.finance.domain.dto.request.UpdateAffiliateRequest;
import com.mumanal.modules.finance.domain.dto.response.AffiliateResponse;
import com.mumanal.modules.finance.domain.repository.AffiliateRepository;
import com.mumanal.modules.finance.persistence.entity.FinAffiliateEntity;
import com.mumanal.modules.finance.persistence.mapper.AffiliateMapper;
import com.mumanal.modules.generic.domain.repository.PersonRepository;
import com.mumanal.modules.generic.persistence.entity.GenPersonEntity;
import com.mumanal.modules.generic.persistence.mapper.PersonMapper;
import com.mumanal.shared.domain.exception.ResourceAlreadyExistsException;
import com.mumanal.shared.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AffiliateServiceImpl implements AffiliateService {

    private final AffiliateRepository affiliateRepository;
    private final PersonRepository personRepository; // Necesitamos acceso directo a Persona
    private final AffiliateMapper affiliateMapper;
    private final PersonMapper personMapper; // Para crear/actualizar la persona

    private final String RESOURCE = "Affiliate";

    public AffiliateServiceImpl(AffiliateRepository affiliateRepository,
                                PersonRepository personRepository,
                                AffiliateMapper affiliateMapper,
                                PersonMapper personMapper) {
        this.affiliateRepository = affiliateRepository;
        this.personRepository = personRepository;
        this.affiliateMapper = affiliateMapper;
        this.personMapper = personMapper;
    }

    @Override
    public List<AffiliateResponse> getAll() {
        return affiliateMapper.toDto(affiliateRepository.findAll());
    }

    @Override
    public AffiliateResponse getById(Integer id) {
        FinAffiliateEntity entity = affiliateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));
        return affiliateMapper.toDto(entity);
    }

    @Override
    @Transactional
    public AffiliateResponse create(CreateAffiliateRequest request) {
        // 1. Validar unicidad del Código de Afiliado
//        if (affiliateRepository.existsByAffiliateCode(request.affiliateCode())) {
//            throw new ResourceAlreadyExistsException(RESOURCE, "code", request.affiliateCode());
//        }

        // 2. Gestionar la Persona (Find or Create)
        GenPersonEntity personEntity;
        Optional<GenPersonEntity> existingPerson = personRepository.findByIdentityCard(request.person().identityCard());

        if (existingPerson.isPresent()) {
            personEntity = existingPerson.get();
            // Validar: ¿Esta persona YA es afiliada?
            if (affiliateRepository.existsByPersonId(personEntity.getId())) {
                throw new ResourceAlreadyExistsException(RESOURCE, "Identity Card", existingPerson.get().getIdentityCard());
            }
            // Opcional: Actualizar datos de persona si vienen nuevos en el request (update implícito)
            // personMapper.updateEntityFromDto(request.person(), personEntity);
        } else {
            // Crear nueva persona
            personEntity = personMapper.toEntity(request.person());
            personEntity = personRepository.save(personEntity);
        }

        // 3. Crear el Afiliado y vincular
        FinAffiliateEntity affiliate = affiliateMapper.toEntity(request);
        affiliate.setPerson(personEntity);
//        affiliate.setStatus(FinAffiliateEntity.AffiliateStatus.valueOf(request.status()));

        return affiliateMapper.toDto(affiliateRepository.save(affiliate));
    }

    @Override
    @Transactional
    public AffiliateResponse update(Integer id, UpdateAffiliateRequest request) {
        FinAffiliateEntity affiliate = affiliateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));

        // Validar cambio de código único
//        if (request.affiliateCode() != null && !request.affiliateCode().equals(affiliate.getAffiliateCode())) {
//            if (affiliateRepository.existsByAffiliateCodeAndIdNot(request.affiliateCode(), id)) {
//                throw new ResourceAlreadyExistsException(RESOURCE, "code", request.affiliateCode());
//            }
//        }

        // Actualizar datos del Afiliado
        affiliateMapper.updateEntityFromDto(request, affiliate);
//        if (request.status() != null) {
//            affiliate.setStatus(FinAffiliateEntity.AffiliateStatus.valueOf(request.status()));
//        }

        // Actualizar datos de la Persona (Cascada manual)
        if (request.person() != null) {
            personMapper.updateEntityFromDto(request.person(), affiliate.getPerson());
            personRepository.save(affiliate.getPerson());
        }

        return affiliateMapper.toDto(affiliateRepository.save(affiliate));
    }

    @Override
    public void delete(Integer id) {
        FinAffiliateEntity entity = affiliateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));
        entity.softDelete();
        affiliateRepository.save(entity);
    }
}
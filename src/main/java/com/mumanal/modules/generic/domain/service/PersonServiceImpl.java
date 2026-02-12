package com.mumanal.modules.generic.domain.service;

import com.mumanal.modules.generic.domain.dto.request.CreatePersonRequest;
import com.mumanal.modules.generic.domain.dto.request.UpdatePersonRequest;
import com.mumanal.modules.generic.domain.dto.response.PersonResponse;
import com.mumanal.modules.generic.domain.repository.PersonRepository;
import com.mumanal.modules.generic.persistence.entity.GenPersonEntity;
import com.mumanal.modules.generic.persistence.mapper.PersonMapper;
import com.mumanal.shared.domain.exception.ResourceAlreadyExistsException;
import com.mumanal.shared.domain.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final String RESOURCE = "Person";

    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    @Override
    public List<PersonResponse> getAll() {
        return personMapper.toDto(personRepository.findAll());
    }

    @Override
    public PersonResponse getById(Integer id) {
        GenPersonEntity entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));
        return personMapper.toDto(entity);
    }

    @Override
    @Transactional
    public PersonResponse create(CreatePersonRequest request) {
        if (personRepository.existsByEmail(request.email())) {
            throw new ResourceAlreadyExistsException(RESOURCE, "email", request.email());
        }
        GenPersonEntity entity = personMapper.toEntity(request);
        return personMapper.toDto(personRepository.save(entity));
    }

    @Override
    @Transactional
    public PersonResponse update(Integer id, UpdatePersonRequest request) {
        GenPersonEntity entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));

        if (request.email() != null && !request.email().equals(entity.getEmail())) {
            if (personRepository.existsByEmailAndIdNot(request.email(), id)) {
                throw new ResourceAlreadyExistsException(RESOURCE, "email", request.email());
            }
        }

        personMapper.updateEntityFromDto(request, entity);
        return personMapper.toDto(personRepository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        GenPersonEntity entity = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));
        entity.softDelete();
        personRepository.save(entity);
    }
}
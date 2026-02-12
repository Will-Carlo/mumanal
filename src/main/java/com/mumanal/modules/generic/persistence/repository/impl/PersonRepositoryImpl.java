package com.mumanal.modules.generic.persistence.repository.impl;

import com.mumanal.modules.generic.domain.repository.PersonRepository;
import com.mumanal.modules.generic.persistence.entity.GenPersonEntity;
import com.mumanal.modules.generic.persistence.repository.jpa.PersonJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepositoryImpl implements PersonRepository {
    private final PersonJpa personJpa;

    public PersonRepositoryImpl(PersonJpa personJpa) {
        this.personJpa = personJpa;
    }

    @Override
    public List<GenPersonEntity> findAll() { return personJpa.findAllByEnabledTrue(); }

    @Override
    public Optional<GenPersonEntity> findById(Integer id) { return personJpa.findByIdAndEnabledTrue(id); }

    @Override
    public GenPersonEntity save(GenPersonEntity entity) { return personJpa.save(entity); }

    @Override
    public boolean existsByEmail(String email) { return personJpa.existsByEmailAndEnabledTrue(email); }

    @Override
    public boolean existsByEmailAndIdNot(String email, Integer id) {
        return personJpa.existsByEmailAndIdNotAndEnabledTrue(email, id);
    }

    @Override
    public Optional<GenPersonEntity> findByIdentityCard(String identityCard) {
        return personJpa.findByIdentityCardAndEnabledTrue(identityCard);
    }
}
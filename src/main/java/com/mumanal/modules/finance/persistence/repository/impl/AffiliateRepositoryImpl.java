package com.mumanal.modules.finance.persistence.repository.impl;

import com.mumanal.modules.finance.domain.repository.AffiliateRepository;
import com.mumanal.modules.finance.persistence.entity.FinAffiliateEntity;
import com.mumanal.modules.finance.persistence.repository.jpa.AffiliateJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AffiliateRepositoryImpl implements AffiliateRepository {
    private final AffiliateJpa affiliateJpa;

    public AffiliateRepositoryImpl(AffiliateJpa affiliateJpa) {
        this.affiliateJpa = affiliateJpa;
    }

    @Override
    public List<FinAffiliateEntity> findAll() { return affiliateJpa.findAllByEnabledTrue(); }

    @Override
    public Optional<FinAffiliateEntity> findById(Integer id) { return affiliateJpa.findByIdAndEnabledTrue(id); }

    @Override
    public FinAffiliateEntity save(FinAffiliateEntity entity) { return affiliateJpa.save(entity); }

//    @Override
//    public boolean existsByAffiliateCode(String code) { return affiliateJpa.existsByAffiliateCodeAndEnabledTrue(code); }

//    @Override
//    public boolean existsByAffiliateCodeAndIdNot(String code, Integer id) {
//        return affiliateJpa.existsByAffiliateCodeAndIdNotAndEnabledTrue(code, id);
//    }

    @Override
    public boolean existsByPersonId(Integer personId) { return affiliateJpa.existsByPersonId(personId); }

    @Override
    public Optional<FinAffiliateEntity> findByPersonIdentityCard(String ci) {
        return affiliateJpa.findByPersonIdentityCardAndEnabledTrue(ci);
    }
}
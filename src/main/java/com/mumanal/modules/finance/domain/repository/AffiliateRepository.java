package com.mumanal.modules.finance.domain.repository;

import com.mumanal.modules.finance.persistence.entity.FinAffiliateEntity;
import java.util.List;
import java.util.Optional;

public interface AffiliateRepository {
    List<FinAffiliateEntity> findAll();
    Optional<FinAffiliateEntity> findById(Integer id);
    FinAffiliateEntity save(FinAffiliateEntity entity);

//    boolean existsByAffiliateCode(String code);
//    boolean existsByAffiliateCodeAndIdNot(String code, Integer id);
    boolean existsByPersonId(Integer personId);

    Optional<FinAffiliateEntity> findByPersonIdentityCard(String identityCard);
}
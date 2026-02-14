package com.mumanal.modules.finance.persistence.repository.jpa;

import com.mumanal.modules.finance.persistence.entity.FinAffiliateEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface AffiliateJpa extends ListCrudRepository<FinAffiliateEntity, Integer> {

    List<FinAffiliateEntity> findAllByEnabledTrue();
    Optional<FinAffiliateEntity> findByIdAndEnabledTrue(Integer id);

//    boolean existsByAffiliateCodeAndEnabledTrue(String affiliateCode);
//    boolean existsByAffiliateCodeAndIdNotAndEnabledTrue(String affiliateCode, Integer id);

    // Verificar si una persona ya es afiliada
    @Query("SELECT COUNT(a) > 0 FROM FinAffiliateEntity a WHERE a.person.id = :personId AND a.enabled = true")
    boolean existsByPersonId(Integer personId);

    Optional<FinAffiliateEntity> findByPersonIdentityCardAndEnabledTrue(String identityCard);
}
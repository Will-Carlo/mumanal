package com.mumanal.modules.security.persistence.repository.jpa;

import com.mumanal.modules.security.persistence.entity.SecUserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserJpa extends ListCrudRepository<SecUserEntity, Integer> {
    Optional<SecUserEntity> findByUsernameAndEnabledTrue(String username);
    boolean existsByUsernameAndEnabledTrue(String username);

    @Query("SELECT u FROM SecUserEntity u" +
            "   WHERE NOT EXISTS (" +
            "       SELECT ar FROM SecAssignedRoleEntity ar " +
            "       JOIN ar.role r " +
            "       WHERE " +
            "           ar.user = u AND " +
            "           r.name = 'ROOT' AND" +
            "           ar.enabled IS TRUE" +
            "   ) AND " +
            "   u.enabled IS TRUE"
    )
    List<SecUserEntity> findAllExcludingRoot();
    List<SecUserEntity> findAllByEnabledTrue();

    Optional<SecUserEntity> findByIdAndEnabledTrue(Integer id);

    boolean existsByUsername(String username);
    Optional<SecUserEntity> findByUsername(String username);
}

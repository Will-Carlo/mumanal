package com.mumanal.modules.security.persistence.repository.impl;

import com.mumanal.modules.security.domain.repository.AssignedRoleRepository;
import com.mumanal.modules.security.persistence.entity.SecAssignedRoleEntity;
import com.mumanal.modules.security.persistence.repository.jpa.AssignedRoleJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AssignedRoleRepositoryImpl implements AssignedRoleRepository {
    private final AssignedRoleJpa assignedRoleJpa;

    public AssignedRoleRepositoryImpl(AssignedRoleJpa assignedRoleJpa) {
        this.assignedRoleJpa = assignedRoleJpa;
    }

    @Override
    public List<SecAssignedRoleEntity> findAll() { return assignedRoleJpa.findAllByEnabledTrue(); }

    @Override
    public List<SecAssignedRoleEntity> findAllByUser(Integer userId) { return assignedRoleJpa.findAllByUserIdAndEnabledTrue(userId); }

    @Override
    public Optional<SecAssignedRoleEntity> findById(Integer id) { return assignedRoleJpa.findByIdAndEnabledTrue(id); }

    @Override
    public SecAssignedRoleEntity save(SecAssignedRoleEntity entity) { return assignedRoleJpa.save(entity); }

    @Override
    public boolean existsAssignment(Integer userId, Integer roleId) {
        return assignedRoleJpa.existsByUserIdAndRoleIdAndEnabledTrue(userId, roleId);
    }
}
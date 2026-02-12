package com.mumanal.modules.security.domain.service;

import com.mumanal.modules.security.domain.dto.request.CreateAssignmentRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateAssignmentRequest;
import com.mumanal.modules.security.domain.dto.response.AssignmentResponse;
import com.mumanal.modules.security.domain.repository.AssignedRoleRepository;
import com.mumanal.modules.security.domain.repository.RoleRepository;
import com.mumanal.modules.security.domain.repository.UserRepository;
import com.mumanal.modules.security.persistence.entity.SecAssignedRoleEntity;
import com.mumanal.modules.security.persistence.entity.SecRoleEntity;
import com.mumanal.modules.security.persistence.entity.SecUserEntity;
import com.mumanal.modules.security.persistence.mapper.AssignedRoleMapper;
import com.mumanal.shared.domain.exception.ResourceAlreadyExistsException;
import com.mumanal.shared.domain.exception.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssignedRoleServiceImpl implements AssignedRoleService {

    private final AssignedRoleRepository assignedRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AssignedRoleMapper mapper;
    private final String RESOURCE = "ROLE ASSIGNMENT";

    public AssignedRoleServiceImpl(AssignedRoleRepository arRepo,
                                   UserRepository uRepo,
                                   RoleRepository rRepo,
                                   AssignedRoleMapper mapper) {
        this.assignedRoleRepository = arRepo;
        this.userRepository = uRepo;
        this.roleRepository = rRepo;
        this.mapper = mapper;
    }

    @Override
    public List<AssignmentResponse> getAll() {
        return mapper.toDto(assignedRoleRepository.findAll());
    }

    @Override
    public List<AssignmentResponse> getAllByUser(Integer userId) {
        return mapper.toDto(assignedRoleRepository.findAllByUser(userId));
    }

    @Override
    public AssignmentResponse getById(Integer id) {
        SecAssignedRoleEntity entity = assignedRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public AssignmentResponse create(CreateAssignmentRequest request) {
        // 1. Validate uniqueness (Avoid active duplicates)
        if (assignedRoleRepository.existsAssignment(request.userId(), request.roleId())) {
            throw new ResourceAlreadyExistsException(RESOURCE, "'user : role'", request.userId() + "' : '" + request.roleId());
        }

        // 2. Search parent entities
        SecUserEntity user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.userId().toString()));

        SecRoleEntity role = roleRepository.findById(request.roleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", request.roleId().toString()));

        // 3. Create entity
        SecAssignedRoleEntity entity = mapper.toEntity(request);
        entity.setUser(user);
        entity.setRole(role);
        entity.setGrantedDate(LocalDateTime.now());
        entity.setGrantedBy(getCurrentUsername());

        return mapper.toDto(assignedRoleRepository.save(entity));
    }

    @Override
    @Transactional
    public AssignmentResponse update(Integer id, UpdateAssignmentRequest request) {
        SecAssignedRoleEntity entity = assignedRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));

        mapper.updateEntityFromDto(request, entity);
        return mapper.toDto(assignedRoleRepository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        SecAssignedRoleEntity entity = assignedRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));
        entity.softDelete();
        assignedRoleRepository.save(entity);
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated()) ? auth.getName() : "SYSTEM";
    }
}
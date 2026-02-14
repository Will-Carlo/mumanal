package com.mumanal.modules.security.domain.service;

import com.mumanal.modules.generic.persistence.entity.GenPersonEntity;
import com.mumanal.modules.security.domain.dto.request.CreateUserRequest;
import com.mumanal.modules.security.domain.dto.request.UpdateUserRequest;
import com.mumanal.modules.security.domain.dto.response.UserProfileResponse;
import com.mumanal.modules.security.domain.dto.response.UserResponse;
import com.mumanal.modules.security.domain.repository.RoleRepository;
import com.mumanal.modules.security.domain.repository.UserRepository;
import com.mumanal.modules.security.persistence.entity.*;
import com.mumanal.modules.security.persistence.mapper.UserMapper;
import com.mumanal.shared.domain.exception.ResourceAlreadyExistsException;
import com.mumanal.shared.domain.exception.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final String RESOURCE = "USER";

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserResponse> getAll() {
        // Lógica de Visibilidad ROOT vs ADMIN
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isRoot = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ROOT"));

        if (isRoot) {
            return userMapper.toDto(userRepository.findAll());
        } else {
            return userMapper.toDto(userRepository.findAllExcludingRoot());
        }
    }

    @Override
    public UserResponse getById(Integer id) {
        return userMapper.toDto(findEntityById(id));
    }

    @Override
    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ResourceAlreadyExistsException(RESOURCE, "username", request.username());
        }

        // 1. Validar Persona (Simulado)
        // GenPersonEntity person = personRepository.findById(request.personId()).orElseThrow(...);
        // Por ahora simulamos la persona para que compile, pero debes usar el Repo real
        GenPersonEntity person = new GenPersonEntity(); // Reemplazar con búsqueda real
        person.setId(request.personId());

        // 2. Crear Usuario
        SecUserEntity user = userMapper.toEntity(request);
        user.setPerson(person);
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        // Guardamos primero para tener ID
        user = userRepository.save(user);

        // 3. Asignar Roles
        assignRolesToUser(user, request.roleIds());

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse update(Integer id, UpdateUserRequest request) {
        SecUserEntity user = findEntityById(id);

        // Actualizar campos simples
        userMapper.updateEntityFromDto(request, user);

        // Actualizar Roles (Estrategia: Limpiar y re-insertar)
        // Nota: Para mantener historial, lo ideal sería marcar como expirados los viejos,
        // pero para este MVP, reemplazamos la lista.
        if (request.roleIds() != null) {
            user.getAssignedRoles().clear(); // Hibernate eliminará los registros huerfanos si CascadeType.ALL + orphanRemoval=true
            assignRolesToUser(user, request.roleIds());
        }

        return userMapper.toDto(userRepository.save(user));
    }

    private void assignRolesToUser(SecUserEntity user, List<Integer> roleIds) {
        if (user.getAssignedRoles() == null) {
            user.setAssignedRoles(new ArrayList<>());
        }

        for (Integer roleId : roleIds) {
            SecRoleEntity role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId.toString()));

            SecAssignedRoleEntity assignment = new SecAssignedRoleEntity();
            assignment.setUser(user);
            assignment.setRole(role);
            assignment.setGrantedDate(LocalDateTime.now());
            // assignment.setGrantedBy(currentUser); // Opcional auditoría

            user.getAssignedRoles().add(assignment);
        }
    }

    private SecUserEntity findEntityById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "id", id.toString()));
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getProfileByUsername(String username) {
        SecUserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE, "username", username));

        // person
        String firstName = "";
        String paternalLastName = "";
        String maternalLastName = "";
        String email = "";
        String imageUrl = "";

        if (user.getPerson() != null) {
            firstName = user.getPerson().getFirstName();
            paternalLastName = user.getPerson().getPaternalSurname();
            maternalLastName = user.getPerson().getMaternalSurname();
            email = user.getPerson().getEmail();
        }

        String fullName = (firstName + " " + paternalLastName + " " + (maternalLastName != null ? maternalLastName : "")).trim();

        // 3. Recolectar Roles y Permisos (Usamos Set para eliminar duplicados automáticamente)
        Set<String> activeRoles = new HashSet<>( );
        Set<String> activePermissions = new HashSet<>();

        if (user.getAssignedRoles() != null) {
            for (SecAssignedRoleEntity assignedRole : user.getAssignedRoles()) {
                // A. Validar que la ASIGNACIÓN esté habilitada (Soft Delete)
                if (!Boolean.TRUE.equals(assignedRole.getEnabled())) continue;

                // B. Validar que el ROL exista y esté habilitado
                SecRoleEntity role = assignedRole.getRole();
                if (role == null || !Boolean.TRUE.equals(role.getEnabled())) continue;

                // Agregar nombre del rol (Ej: "ADMIN", "STORE_MANAGER")
                activeRoles.add(role.getName());

                // C. Extraer permisos del rol
                if (role.getPermissions() != null) {
                    for (SecAssignedPermissionEntity assignedPerm : role.getPermissions()) {
                        // Validar asignación de permiso
                        if (!Boolean.TRUE.equals(assignedPerm.getEnabled())) continue;

                        SecPermissionEntity permission = assignedPerm.getPermission();
                        // Validar entidad permiso
                        if (permission != null &&
                                Boolean.TRUE.equals(permission.getEnabled()) &&
                                Boolean.TRUE.equals(permission.getStatus())) {

                            activePermissions.add(permission.getCode());
                        }
                    }
                }
            }
        }
        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                firstName,
                paternalLastName,
                maternalLastName,
                fullName,
                email,
                imageUrl,
                activeRoles,
                activePermissions
        );
    }
}

package com.mumanal.modules.security.domain.service;

import com.mumanal.modules.security.domain.model.CustomUserDetails;
import com.mumanal.modules.security.domain.repository.UserRepository;
import com.mumanal.modules.security.persistence.entity.SecAssignedPermissionEntity;
import com.mumanal.modules.security.persistence.entity.SecAssignedRoleEntity;
import com.mumanal.modules.security.persistence.entity.SecUserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public SecurityUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecUserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found."));

        List<GrantedAuthority> authorities = new ArrayList<>();

        // 1. Load Roles and Permissions
        if (user.getAssignedRoles() != null) {
            for (SecAssignedRoleEntity assignedRole : user.getAssignedRoles()) {
                // Roles
                String roleName = "ROLE_" + assignedRole.getRole().getName().toUpperCase();
                authorities.add(new SimpleGrantedAuthority(roleName));

                // Permissions
                if (assignedRole.getRole().getPermissions() != null) {
                    for (SecAssignedPermissionEntity rolePerm : assignedRole.getRole().getPermissions()) {
                        String permCode = rolePerm.getPermission().getCode();
                        authorities.add(new SimpleGrantedAuthority(permCode));
                    }
                }
            }
        }

        return new CustomUserDetails(
                user.getUsername(),
                user.getPasswordHash(),
                !user.getDisabled(),
                true,
                true,
                !user.getLocked(),
                authorities,
                user.getPerson().getEmail()
        );

//        return User.builder()
//                .username(user.getUsername())
//                .password(user.getPasswordHash())
//                .authorities(authorities)
//                .accountLocked(user.getLocked())
//                .disabled(user.getDisabled())
//                .build();
    }
}

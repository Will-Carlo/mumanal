package com.mumanal.modules.security.persistence.entity;

import com.mumanal.modules.generic.persistence.entity.GenPersonEntity;
import com.mumanal.shared.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "sec_users")
public class SecUserEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "status_type", length = 20, nullable = false)
    private Integer statusType; // activo, inactivo, baneado, temporalmente de baja

    @Column(nullable = false)
    private Boolean locked;

    @Column(nullable = false)
    private Boolean disabled;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "deactivated_at")
    private LocalDateTime deactivatedAt;

    @OneToOne(optional = false) @JoinColumn(name = "person_id")
    private GenPersonEntity person;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SecAssignedRoleEntity> assignedRoles;
}


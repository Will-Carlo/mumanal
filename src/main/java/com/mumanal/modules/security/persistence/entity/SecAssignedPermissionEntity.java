package com.mumanal.modules.security.persistence.entity;

import com.mumanal.shared.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "sec_assigned_permissions")
public class SecAssignedPermissionEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne @JoinColumn(name = "role_id", nullable = false)
    private SecRoleEntity role;

    @ManyToOne @JoinColumn(name = "permission_id", nullable = false)
    private SecPermissionEntity permission;
}

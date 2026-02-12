package com.mumanal.modules.security.persistence.entity;

import com.mumanal.shared.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "sec_assigned_roles")
public class SecAssignedRoleEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false) @JoinColumn(name = "user_id")
    private SecUserEntity user;

    @ManyToOne(optional = false) @JoinColumn(name = "role_id")
    private SecRoleEntity role;

    @Column(name = "granted_BY")
    private String grantedBy;

    @Column(name = "granted_date")
    private LocalDateTime grantedDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
}

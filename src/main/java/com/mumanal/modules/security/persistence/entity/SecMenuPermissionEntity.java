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
@Entity @Table(name = "sec_menu_permissions")
public class SecMenuPermissionEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne @JoinColumn(name = "menu_id")
    private SecMenuEntity menu;

    @ManyToOne @JoinColumn(name = "permission_id")
    private SecPermissionEntity permission;
}
 
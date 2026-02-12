package com.mumanal.modules.security.persistence.entity;

import com.mumanal.shared.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "sec_menus")
public class SecMenuEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 200)
    private String route;

    @Column(length = 60)
    private String icon;

    @Column(nullable = false)
    private Integer sortOrder = 0;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic = Boolean.FALSE;

    @Column(nullable = false)
    private Boolean status = Boolean.TRUE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_menu_id")
    private SecMenuEntity parentMenu;

    @OneToMany(mappedBy = "parentMenu", fetch = FetchType.LAZY)
    @OrderBy("sortOrder ASC")
    private List<SecMenuEntity> subMenus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sec_menu_permissions",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<SecPermissionEntity> requiredPermissions;
}

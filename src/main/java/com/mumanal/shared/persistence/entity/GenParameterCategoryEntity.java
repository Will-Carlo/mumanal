package com.mumanal.shared.persistence.entity;

import com.mumanal.shared.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "gen_parameter_categories")
public class GenParameterCategoryEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ESTA ES LA CLAVE: Un código texto único.
    // Ej: "CLIENT_TYPE", "PAYMENT_METHOD", "GENDER"
    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String name; // Ej: "Tipos de Cliente"

    @Column(length = 255)
    private String description;

    @Column
    private Boolean status = Boolean.TRUE;
}
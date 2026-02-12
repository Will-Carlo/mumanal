package com.mumanal.shared.persistence.entity;

import com.mumanal.shared.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "gen_parameters")
public class GenParameterEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // El valor real que guardas en tus otras tablas (701, 301, etc)
    @Column(name = "numeric_code", nullable = false)
    private Integer numericCode;

    @Column(nullable = false, length = 100)
    private String name; // Ej: "Estudiante", "QR"

    @Column(length = 255)
    private String description;

    // Orden para mostrar en el combo (1, 2, 3...)
    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column
    private Boolean status = Boolean.TRUE;

    // Relación con la categoría padre
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private GenParameterCategoryEntity category;
}
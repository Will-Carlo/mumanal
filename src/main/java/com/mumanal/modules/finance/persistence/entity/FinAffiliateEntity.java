package com.mumanal.modules.finance.persistence.entity;

import com.mumanal.modules.generic.persistence.entity.GenPersonEntity;
import com.mumanal.shared.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "fin_affiliates")
public class FinAffiliateEntity extends AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false, unique = true)
    private GenPersonEntity person;

//    @Column(name = "affiliate_code", length = 20, nullable = false)
//    private String affiliateCode; // Ej: Nro de Item, Matrícula, Cód. Seguro

//    @Column(name = "admission_date")
//    private LocalDate admissionDate; // Desde cuándo es profesor/aportante

//    @Column(name = "status", length = 20)
//    @Enumerated(EnumType.STRING)
//    private AffiliateStatus status; // ACTIVE, RETIRED, PASSIVE

    @OneToMany(mappedBy = "affiliate", cascade = CascadeType.ALL)
    private List<FinVoucherEntity> vouchers;
}
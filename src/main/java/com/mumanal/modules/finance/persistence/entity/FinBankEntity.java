package com.mumanal.modules.finance.persistence.entity;


import com.mumanal.shared.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name  = "fin_banks")
public class FinBankEntity extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "bank_code")
    private String bankCode; // Ej: "UNION", "BNB", "BCP"

    @OneToMany(mappedBy = "bank")
    private List<FinVoucherEntity> vouchers;
}

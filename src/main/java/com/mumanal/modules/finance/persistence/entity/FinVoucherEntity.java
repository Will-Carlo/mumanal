package com.mumanal.modules.finance.persistence.entity;

import com.mumanal.modules.generic.persistence.entity.GenPersonEntity;
import com.mumanal.shared.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name  = "fin_vouchers")
public class FinVoucherEntity extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "deposit_number", nullable = false)
    private Integer depositNumber;

    @Column(name = "deposit_date", nullable = false)
    private LocalDateTime depositDate;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

//    @Column(name = "bank_name")
//    private String bankName;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate period;

//    @Column(name = "account_number")
//    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", nullable = false)
    private FinBankEntity bank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private GenPersonEntity person;

}

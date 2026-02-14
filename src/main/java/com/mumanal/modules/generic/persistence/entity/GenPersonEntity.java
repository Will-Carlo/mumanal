package com.mumanal.modules.generic.persistence.entity;

import com.mumanal.shared.persistence.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gen_persons")
public class GenPersonEntity extends AuditableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "second_name", length = 100)
    private String secondName;

    @Column(name = "paternal_surname", length = 45)
    private String paternalSurname;

    @Column(name = "maternal_surname", length = 45)
    private String maternalSurname;

    @Column(name = "phone_number", length = 45)
    private Integer phoneNumber;

    @Column(length = 100)
    private String email;

    @Column(name = "identity_card", length = 45)
    private String identityCard;
}

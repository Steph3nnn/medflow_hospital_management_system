package com.ochai.medflow.doctor.entity;

import com.ochai.medflow.common.enums.Department;
import com.ochai.medflow.common.enums.Gender;
import com.ochai.medflow.common.enums.Specialty;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String doctorId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    private Integer yearsOfExperience;

    private BigDecimal consultationFee;

    @Builder.Default
    private boolean active = true;

}
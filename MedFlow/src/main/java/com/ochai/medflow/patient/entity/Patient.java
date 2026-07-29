package com.ochai.medflow.patient.entity;

import com.ochai.medflow.common.enums.BloodGroup;
import com.ochai.medflow.common.enums.Gender;
import com.ochai.medflow.common.enums.Genotype;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String patientId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String phoneNumber;

    private String email;

    private String address;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @Enumerated(EnumType.STRING)
    private Genotype genotype;

    @Column(length = 1000)
    private String allergies;

    private String emergencyContactName;

    private String emergencyContactPhone;

    @Builder.Default
    private boolean active = true;
}
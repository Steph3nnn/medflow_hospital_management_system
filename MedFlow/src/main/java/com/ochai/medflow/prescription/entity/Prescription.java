package com.ochai.medflow.prescription.entity;

import com.ochai.medflow.consultation.entity.Consultation;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String prescriptionId;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    private String medication;

    private String dosage;

    private String frequency;

    private String duration;

    private String route;

    @Column(length = 1000)
    private String instructions;

}
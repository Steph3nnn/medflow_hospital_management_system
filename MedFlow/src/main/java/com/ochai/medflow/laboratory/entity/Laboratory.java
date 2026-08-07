package com.ochai.medflow.laboratory.entity;

import com.ochai.medflow.consultation.entity.Consultation;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Laboratory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String laboratoryId;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    private String testName;

    private String sampleType;

    private String priority;

    @Column(length = 1000)
    private String clinicalNotes;

    private String status;

    @Column(length = 3000)
    private String result;

}
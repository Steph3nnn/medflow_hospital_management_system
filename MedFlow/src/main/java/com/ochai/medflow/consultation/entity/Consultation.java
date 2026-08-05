package com.ochai.medflow.consultation.entity;

import com.ochai.medflow.appointment.entity.Appointment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String consultationId;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private LocalDate consultationDate;

    @Column(length = 1000)
    private String chiefComplaint;

    @Column(length = 2000)
    private String historyOfPresentIllness;

    @Column(length = 2000)
    private String physicalExamination;

    @Column(length = 2000)
    private String diagnosis;

    @Column(length = 2000)
    private String treatmentPlan;

    @Column(length = 2000)
    private String notes;
}
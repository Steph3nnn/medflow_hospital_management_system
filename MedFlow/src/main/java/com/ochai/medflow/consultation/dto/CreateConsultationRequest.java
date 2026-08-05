package com.ochai.medflow.consultation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateConsultationRequest {

    @NotNull
    private Long appointmentId;

    @NotNull
    private LocalDate consultationDate;

    @NotBlank
    private String chiefComplaint;

    @NotBlank
    private String historyOfPresentIllness;

    @NotBlank
    private String physicalExamination;

    @NotBlank
    private String diagnosis;

    @NotBlank
    private String treatmentPlan;

    private String notes;

}
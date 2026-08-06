package com.ochai.medflow.prescription.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePrescriptionRequest {

    @NotNull
    private Long consultationId;

    @NotBlank
    private String medication;

    @NotBlank
    private String dosage;

    @NotBlank
    private String frequency;

    @NotBlank
    private String duration;

    @NotBlank
    private String route;

    private String instructions;

}
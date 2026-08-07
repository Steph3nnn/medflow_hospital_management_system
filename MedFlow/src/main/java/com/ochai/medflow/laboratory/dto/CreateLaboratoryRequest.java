package com.ochai.medflow.laboratory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLaboratoryRequest {

    @NotNull
    private Long consultationId;

    @NotBlank
    private String testName;

    @NotBlank
    private String sampleType;

    @NotBlank
    private String priority;

    @NotBlank
    private String clinicalNotes;

    private String status;

    private String result;

}
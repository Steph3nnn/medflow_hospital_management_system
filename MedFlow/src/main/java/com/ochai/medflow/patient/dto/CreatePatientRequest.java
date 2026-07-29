package com.ochai.medflow.patient.dto;

import com.ochai.medflow.common.enums.BloodGroup;
import com.ochai.medflow.common.enums.Gender;
import com.ochai.medflow.common.enums.Genotype;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePatientRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Gender gender;

    @NotNull
    private LocalDate dateOfBirth;

    @NotBlank
    private String phoneNumber;

    @Email
    private String email;

    private String address;

    @NotNull
    private BloodGroup bloodGroup;

    @NotNull
    private Genotype genotype;

    private String allergies;

    @NotBlank
    private String emergencyContactName;

    @NotBlank
    private String emergencyContactPhone;

}
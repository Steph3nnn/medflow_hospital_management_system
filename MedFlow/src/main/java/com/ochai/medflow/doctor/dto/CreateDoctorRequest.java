package com.ochai.medflow.doctor.dto;

import com.ochai.medflow.common.enums.Department;
import com.ochai.medflow.common.enums.Gender;
import com.ochai.medflow.common.enums.Specialty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateDoctorRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Gender gender;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phoneNumber;

    @NotNull
    private Department department;

    @NotNull
    private Specialty specialty;

    @NotBlank
    private String licenseNumber;

    @NotNull
    @Min(0)
    private Integer yearsOfExperience;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal consultationFee;
}
package com.ochai.medflow.pharmacy.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreatePharmacyRequest {

    @NotBlank
    private String medicationCode;

    @NotBlank
    private String medicationName;

    private String category;

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal unitPrice;

    private String supplier;

    private LocalDate expiryDate;

    private String description;
}
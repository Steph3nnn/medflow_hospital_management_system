package com.ochai.medflow.billing.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateBillingRequest {

    @NotNull
    private Long appointmentId;

    @NotNull
    private BigDecimal consultationFee;

    @NotNull
    private BigDecimal laboratoryFee;

    @NotNull
    private BigDecimal medicationFee;

    @NotNull
    private BigDecimal otherCharges;

    @NotNull
    private BigDecimal amountPaid;

    private String notes;
}
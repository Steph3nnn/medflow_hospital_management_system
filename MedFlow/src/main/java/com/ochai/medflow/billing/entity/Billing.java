package com.ochai.medflow.billing.entity;

import com.ochai.medflow.appointment.entity.Appointment;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String billId;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private BigDecimal consultationFee;

    private BigDecimal laboratoryFee;

    private BigDecimal medicationFee;

    private BigDecimal otherCharges;

    private BigDecimal totalAmount;

    private BigDecimal amountPaid;

    private BigDecimal balance;

    private String paymentStatus;

    private LocalDateTime billingDate;

    @Column(length = 1000)
    private String notes;
}
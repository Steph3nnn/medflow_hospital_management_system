package com.ochai.medflow.pharmacy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pharmacy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String medicationCode;

    @Column(nullable = false)
    private String medicationName;

    private String category;

    private Integer quantity;

    private BigDecimal unitPrice;

    private String supplier;

    private LocalDate expiryDate;

    @Column(length = 1000)
    private String description;

    public String getStockStatus() {

        if (quantity == null || quantity <= 0) {
            return "OUT OF STOCK";
        }

        if (quantity <= 10) {
            return "LOW STOCK";
        }

        return "IN STOCK";
    }

    public boolean isExpired() {

        return expiryDate != null
                && expiryDate.isBefore(LocalDate.now());
    }
}
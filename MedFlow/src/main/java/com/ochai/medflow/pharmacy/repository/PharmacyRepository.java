package com.ochai.medflow.pharmacy.repository;

import com.ochai.medflow.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

    Optional<Pharmacy> findByMedicationCode(String medicationCode);

    List<Pharmacy> findByMedicationNameContainingIgnoreCase(
            String medicationName
    );

    List<Pharmacy> findByMedicationCodeContainingIgnoreCase(
            String medicationCode
    );

    long countByQuantityGreaterThan(Integer quantity);

    long countByQuantityLessThanEqualAndQuantityGreaterThan(
            Integer maximum,
            Integer minimum
    );

    long countByQuantity(Integer quantity);

    long countByExpiryDateBefore(LocalDate date);
}
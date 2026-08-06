package com.ochai.medflow.prescription.repository;

import com.ochai.medflow.prescription.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    Optional<Prescription> findByPrescriptionId(String prescriptionId);

    List<Prescription> findByPrescriptionIdContainingIgnoreCase(String keyword);

}
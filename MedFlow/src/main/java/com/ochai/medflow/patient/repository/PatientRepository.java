package com.ochai.medflow.patient.repository;

import com.ochai.medflow.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByPatientId(String patientId);

   

    List<Patient> findByPatientIdContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(
            String patientId,
            String firstName,
            String lastName,
            String phoneNumber
    );

}
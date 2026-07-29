package com.ochai.medflow.patient.service;

import com.ochai.medflow.patient.dto.CreatePatientRequest;
import com.ochai.medflow.patient.entity.Patient;
import com.ochai.medflow.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public void createPatient(CreatePatientRequest request) {

        Patient patient = Patient.builder()
                .patientId(generatePatientId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .address(request.getAddress())
                .bloodGroup(request.getBloodGroup())
                .genotype(request.getGenotype())
                .allergies(request.getAllergies())
                .emergencyContactName(request.getEmergencyContactName())
                .emergencyContactPhone(request.getEmergencyContactPhone())
                .build();

        patientRepository.save(patient);
    }

    private String generatePatientId() {

        long count = patientRepository.count() + 1;

        return String.format(
                "PT-%d-%06d",
                Year.now().getValue(),
                count
        );
    }
    
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

}
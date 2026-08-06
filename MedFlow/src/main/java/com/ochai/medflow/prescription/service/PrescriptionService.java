package com.ochai.medflow.prescription.service;

import com.ochai.medflow.consultation.entity.Consultation;
import com.ochai.medflow.consultation.repository.ConsultationRepository;
import com.ochai.medflow.prescription.dto.CreatePrescriptionRequest;
import com.ochai.medflow.prescription.entity.Prescription;
import com.ochai.medflow.prescription.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final ConsultationRepository consultationRepository;

    public List<Prescription> findAll() {

        return prescriptionRepository.findAll();

    }

    public List<Prescription> search(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return prescriptionRepository.findAll();
        }

        return prescriptionRepository
                .findByPrescriptionIdContainingIgnoreCase(keyword);

    }

    public Prescription findById(Long id) {

        return prescriptionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Prescription not found"));

    }

    public void createPrescription(CreatePrescriptionRequest request) {

        Consultation consultation = consultationRepository.findById(request.getConsultationId())
                .orElseThrow(() ->
                        new RuntimeException("Consultation not found"));

        Prescription prescription = Prescription.builder()
                .prescriptionId(generatePrescriptionId())
                .consultation(consultation)
                .medication(request.getMedication())
                .dosage(request.getDosage())
                .frequency(request.getFrequency())
                .duration(request.getDuration())
                .route(request.getRoute())
                .instructions(request.getInstructions())
                .build();

        prescriptionRepository.save(prescription);

    }

    public void updatePrescription(Long id,
                                   CreatePrescriptionRequest request) {

        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Prescription not found"));

        Consultation consultation = consultationRepository.findById(request.getConsultationId())
                .orElseThrow(() ->
                        new RuntimeException("Consultation not found"));

        prescription.setConsultation(consultation);
        prescription.setMedication(request.getMedication());
        prescription.setDosage(request.getDosage());
        prescription.setFrequency(request.getFrequency());
        prescription.setDuration(request.getDuration());
        prescription.setRoute(request.getRoute());
        prescription.setInstructions(request.getInstructions());

        prescriptionRepository.save(prescription);

    }

    public void delete(Long id) {

        prescriptionRepository.deleteById(id);

    }

    private String generatePrescriptionId() {

        long count = prescriptionRepository.count() + 1;

        return String.format("PRE-%06d", count);

    }

}
package com.ochai.medflow.pharmacy.service;

import com.ochai.medflow.pharmacy.dto.CreatePharmacyRequest;
import com.ochai.medflow.pharmacy.entity.Pharmacy;
import com.ochai.medflow.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    public List<Pharmacy> findAll() {

        return pharmacyRepository.findAll();
    }

    public List<Pharmacy> search(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return pharmacyRepository.findAll();
        }

        return pharmacyRepository
                .findByMedicationNameContainingIgnoreCase(keyword);
    }

    public Pharmacy findById(Long id) {

        return pharmacyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Medication not found"));
    }

    public void createMedication(
            CreatePharmacyRequest request) {

        Pharmacy pharmacy = Pharmacy.builder()
                .medicationCode(request.getMedicationCode())
                .medicationName(request.getMedicationName())
                .category(request.getCategory())
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .supplier(request.getSupplier())
                .expiryDate(request.getExpiryDate())
                .description(request.getDescription())
                .build();

        pharmacyRepository.save(pharmacy);
    }

    public void updateMedication(
            Long id,
            CreatePharmacyRequest request) {

        Pharmacy pharmacy = findById(id);

        pharmacy.setMedicationCode(
                request.getMedicationCode()
        );

        pharmacy.setMedicationName(
                request.getMedicationName()
        );

        pharmacy.setCategory(
                request.getCategory()
        );

        pharmacy.setQuantity(
                request.getQuantity()
        );

        pharmacy.setUnitPrice(
                request.getUnitPrice()
        );

        pharmacy.setSupplier(
                request.getSupplier()
        );

        pharmacy.setExpiryDate(
                request.getExpiryDate()
        );

        pharmacy.setDescription(
                request.getDescription()
        );

        pharmacyRepository.save(pharmacy);
    }

    public void deleteMedication(Long id) {

        pharmacyRepository.deleteById(id);
    }
}
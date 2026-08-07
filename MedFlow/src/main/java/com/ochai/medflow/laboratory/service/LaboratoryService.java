package com.ochai.medflow.laboratory.service;

import com.ochai.medflow.consultation.entity.Consultation;
import com.ochai.medflow.consultation.repository.ConsultationRepository;
import com.ochai.medflow.laboratory.dto.CreateLaboratoryRequest;
import com.ochai.medflow.laboratory.entity.Laboratory;
import com.ochai.medflow.laboratory.repository.LaboratoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaboratoryService {

    private final LaboratoryRepository laboratoryRepository;
    private final ConsultationRepository consultationRepository;

    public List<Laboratory> findAll() {
        return laboratoryRepository.findAll();
    }

    public List<Laboratory> search(String keyword){

        if(keyword==null || keyword.isBlank()){
            return laboratoryRepository.findAll();
        }

        return laboratoryRepository
                .findByLaboratoryIdContainingIgnoreCase(keyword);

    }

    public Laboratory findById(Long id){

        return laboratoryRepository.findById(id)
                .orElseThrow(()->
                        new RuntimeException("Laboratory request not found"));

    }

    public void createLaboratory(CreateLaboratoryRequest request){

        Consultation consultation =
                consultationRepository.findById(request.getConsultationId())
                        .orElseThrow(()->
                                new RuntimeException("Consultation not found"));

        Laboratory laboratory = Laboratory.builder()
                .laboratoryId(generateLaboratoryId())
                .consultation(consultation)
                .testName(request.getTestName())
                .sampleType(request.getSampleType())
                .priority(request.getPriority())
                .clinicalNotes(request.getClinicalNotes())
                .status(request.getStatus())
                .result(request.getResult())
                .build();

        laboratoryRepository.save(laboratory);

    }

    public void updateLaboratory(Long id,
                                 CreateLaboratoryRequest request){

        Laboratory laboratory =
                laboratoryRepository.findById(id)
                        .orElseThrow(()->
                                new RuntimeException("Laboratory request not found"));

        Consultation consultation =
                consultationRepository.findById(request.getConsultationId())
                        .orElseThrow(()->
                                new RuntimeException("Consultation not found"));

        laboratory.setConsultation(consultation);
        laboratory.setTestName(request.getTestName());
        laboratory.setSampleType(request.getSampleType());
        laboratory.setPriority(request.getPriority());
        laboratory.setClinicalNotes(request.getClinicalNotes());
        laboratory.setStatus(request.getStatus());
        laboratory.setResult(request.getResult());

        laboratoryRepository.save(laboratory);

    }

    public void delete(Long id){

        laboratoryRepository.deleteById(id);

    }

    private String generateLaboratoryId(){

        long count = laboratoryRepository.count()+1;

        return String.format("LAB-%06d",count);

    }

}
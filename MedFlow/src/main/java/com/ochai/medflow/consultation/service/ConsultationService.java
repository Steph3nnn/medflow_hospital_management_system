package com.ochai.medflow.consultation.service;

import com.ochai.medflow.appointment.entity.Appointment;
import com.ochai.medflow.appointment.repository.AppointmentRepository;
import com.ochai.medflow.consultation.dto.CreateConsultationRequest;
import com.ochai.medflow.consultation.entity.Consultation;
import com.ochai.medflow.consultation.repository.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final AppointmentRepository appointmentRepository;

    public List<Consultation> findAll() {

        return consultationRepository.findAll();

    }

    public void createConsultation(CreateConsultationRequest request) {

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Consultation consultation = Consultation.builder()
                .consultationId(generateConsultationId())
                .appointment(appointment)
                .consultationDate(request.getConsultationDate())
                .chiefComplaint(request.getChiefComplaint())
                .historyOfPresentIllness(request.getHistoryOfPresentIllness())
                .physicalExamination(request.getPhysicalExamination())
                .diagnosis(request.getDiagnosis())
                .treatmentPlan(request.getTreatmentPlan())
                .notes(request.getNotes())
                .build();

        consultationRepository.save(consultation);

    }


    private String generateConsultationId() {

        long count = consultationRepository.count() + 1;

        return String.format("CON-%02d", count);

    }

    public Consultation findById(Long id) {

        return consultationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Consultation not found"));

    }

    public void delete(Long id) {

        consultationRepository.deleteById(id);

    }

    public List<Consultation> search(String keyword) {

        if (keyword == null || keyword.isBlank()) {

            return consultationRepository.findAll();

        }

        return consultationRepository
                .findByConsultationIdContainingIgnoreCase(keyword);

    }

    public void updateConsultation(Long id,
                                   CreateConsultationRequest request) {

        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Consultation not found"));

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() ->
                        new RuntimeException("Appointment not found"));

        consultation.setAppointment(appointment);

        consultation.setConsultationDate(request.getConsultationDate());

        consultation.setChiefComplaint(request.getChiefComplaint());

        consultation.setHistoryOfPresentIllness(
                request.getHistoryOfPresentIllness());

        consultation.setPhysicalExamination(
                request.getPhysicalExamination());

        consultation.setDiagnosis(
                request.getDiagnosis());

        consultation.setTreatmentPlan(
                request.getTreatmentPlan());

        consultation.setNotes(
                request.getNotes());

        consultationRepository.save(consultation);

    }


}
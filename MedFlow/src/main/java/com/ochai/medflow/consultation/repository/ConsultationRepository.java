package com.ochai.medflow.consultation.repository;

import com.ochai.medflow.consultation.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    Optional<Consultation> findByConsultationId(String consultationId);

    List<Consultation> findByConsultationIdContainingIgnoreCase(String consultationId);
}
package com.ochai.medflow.laboratory.repository;

import com.ochai.medflow.laboratory.entity.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LaboratoryRepository extends JpaRepository<Laboratory,Long>{

    Optional<Laboratory> findByLaboratoryId(String laboratoryId);

    List<Laboratory> findByLaboratoryIdContainingIgnoreCase(String keyword);

}
package com.ochai.medflow.billing.repository;

import com.ochai.medflow.billing.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillingRepository extends JpaRepository<Billing, Long> {

    Optional<Billing> findByBillId(String billId);

    List<Billing> findByBillIdContainingIgnoreCase(String keyword);
}
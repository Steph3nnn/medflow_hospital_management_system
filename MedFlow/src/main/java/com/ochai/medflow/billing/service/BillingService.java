package com.ochai.medflow.billing.service;

import com.ochai.medflow.appointment.entity.Appointment;
import com.ochai.medflow.appointment.repository.AppointmentRepository;
import com.ochai.medflow.billing.dto.CreateBillingRequest;
import com.ochai.medflow.billing.entity.Billing;
import com.ochai.medflow.billing.repository.BillingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillingRepository billingRepository;
    private final AppointmentRepository appointmentRepository;

    public List<Billing> findAll() {

        return billingRepository.findAll();
    }

    public List<Billing> search(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return billingRepository.findAll();
        }

        return billingRepository
                .findByBillIdContainingIgnoreCase(keyword);
    }

    public Billing findById(Long id) {

        return billingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Bill not found"));
    }

    public void createBilling(CreateBillingRequest request) {

        Appointment appointment =
                appointmentRepository.findById(request.getAppointmentId())
                        .orElseThrow(() ->
                                new RuntimeException("Appointment not found"));

        BigDecimal totalAmount = calculateTotal(request);

        BigDecimal amountPaid = request.getAmountPaid();

        BigDecimal balance = totalAmount.subtract(amountPaid);

        Billing billing = Billing.builder()
                .billId(generateBillId())
                .appointment(appointment)
                .consultationFee(request.getConsultationFee())
                .laboratoryFee(request.getLaboratoryFee())
                .medicationFee(request.getMedicationFee())
                .otherCharges(request.getOtherCharges())
                .totalAmount(totalAmount)
                .amountPaid(amountPaid)
                .balance(balance)
                .paymentStatus(determinePaymentStatus(totalAmount, amountPaid))
                .billingDate(LocalDateTime.now())
                .notes(request.getNotes())
                .build();

        billingRepository.save(billing);
    }

    public void updateBilling(
            Long id,
            CreateBillingRequest request) {

        Billing billing = findById(id);

        Appointment appointment =
                appointmentRepository.findById(request.getAppointmentId())
                        .orElseThrow(() ->
                                new RuntimeException("Appointment not found"));

        BigDecimal totalAmount = calculateTotal(request);

        BigDecimal amountPaid = request.getAmountPaid();

        BigDecimal balance = totalAmount.subtract(amountPaid);

        billing.setAppointment(appointment);
        billing.setConsultationFee(request.getConsultationFee());
        billing.setLaboratoryFee(request.getLaboratoryFee());
        billing.setMedicationFee(request.getMedicationFee());
        billing.setOtherCharges(request.getOtherCharges());
        billing.setTotalAmount(totalAmount);
        billing.setAmountPaid(amountPaid);
        billing.setBalance(balance);
        billing.setPaymentStatus(
                determinePaymentStatus(totalAmount, amountPaid)
        );
        billing.setNotes(request.getNotes());

        billingRepository.save(billing);
    }

    public void delete(Long id) {

        billingRepository.deleteById(id);
    }

    private BigDecimal calculateTotal(CreateBillingRequest request) {

        return request.getConsultationFee()
                .add(request.getLaboratoryFee())
                .add(request.getMedicationFee())
                .add(request.getOtherCharges());
    }

    private String determinePaymentStatus(
            BigDecimal total,
            BigDecimal paid) {

        if (paid.compareTo(BigDecimal.ZERO) <= 0) {
            return "UNPAID";
        }

        if (paid.compareTo(total) >= 0) {
            return "PAID";
        }

        return "PARTIALLY PAID";
    }

    private String generateBillId() {

        long count = billingRepository.count() + 1;

        return String.format("BILL-%06d", count);
    }
}
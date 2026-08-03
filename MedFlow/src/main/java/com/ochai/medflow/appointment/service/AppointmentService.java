package com.ochai.medflow.appointment.service;

import com.ochai.medflow.appointment.dto.CreateAppointmentRequest;
import com.ochai.medflow.appointment.entity.Appointment;
import com.ochai.medflow.appointment.repository.AppointmentRepository;
import com.ochai.medflow.common.enums.AppointmentStatus;
import com.ochai.medflow.doctor.entity.Doctor;
import com.ochai.medflow.doctor.service.DoctorService;
import com.ochai.medflow.patient.entity.Patient;
import com.ochai.medflow.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public void createAppointment(CreateAppointmentRequest request) {

        Patient patient = patientService.getPatientById(request.getPatientId());

        Doctor doctor = doctorService.getDoctorById(request.getDoctorId());

        Appointment appointment = Appointment.builder()
                .appointmentId(generateAppointmentId())
                .patient(patient)
                .doctor(doctor)
                .appointmentDate(request.getAppointmentDate())
                .appointmentTime(request.getAppointmentTime())
                .reason(request.getReason())
                .notes(request.getNotes())
                .status(AppointmentStatus.SCHEDULED)
                .build();

        appointmentRepository.save(appointment);
    }

    public Appointment getAppointmentById(Long id) {

        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public void updateAppointment(Long id, CreateAppointmentRequest request) {

        Appointment appointment = getAppointmentById(id);

        appointment.setPatient(
                patientService.getPatientById(request.getPatientId())
        );

        appointment.setDoctor(
                doctorService.getDoctorById(request.getDoctorId())
        );

        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setNotes(request.getNotes());

        appointmentRepository.save(appointment);
    }

    public List<Appointment> searchAppointments(String search) {

        if (search == null || search.trim().isEmpty()) {
            return appointmentRepository.findAll();
        }

        return appointmentRepository
                .findByAppointmentIdContainingIgnoreCaseOrPatient_FirstNameContainingIgnoreCaseOrPatient_LastNameContainingIgnoreCaseOrDoctor_FirstNameContainingIgnoreCaseOrDoctor_LastNameContainingIgnoreCase(
                        search,
                        search,
                        search,
                        search,
                        search
                );
    }

    public void updateStatus(Long id, AppointmentStatus status) {

        Appointment appointment = getAppointmentById(id);

        appointment.setStatus(status);

        appointmentRepository.save(appointment);
    }

    private String generateAppointmentId() {

        long count = appointmentRepository.count() + 1;

        return String.format(
                "APT-%d-%06d",
                Year.now().getValue(),
                count
        );
    }

    public long countAppointments() {
        return appointmentRepository.count();
    }

}
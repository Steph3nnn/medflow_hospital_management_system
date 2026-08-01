package com.ochai.medflow.appointment.repository;

import com.ochai.medflow.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByAppointmentId(String appointmentId);

    List<Appointment> findByAppointmentIdContainingIgnoreCaseOrPatient_FirstNameContainingIgnoreCaseOrPatient_LastNameContainingIgnoreCaseOrDoctor_FirstNameContainingIgnoreCaseOrDoctor_LastNameContainingIgnoreCase(
            String appointmentId,
            String patientFirstName,
            String patientLastName,
            String doctorFirstName,
            String doctorLastName
    );
}
package com.ochai.medflow.authentication.controller;

import com.ochai.medflow.appointment.service.AppointmentService;
import com.ochai.medflow.authentication.service.UserService;
import com.ochai.medflow.doctor.service.DoctorService;
import com.ochai.medflow.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("patientCount", patientService.countPatients());
        model.addAttribute("doctorCount", doctorService.countDoctors());
        model.addAttribute("appointmentCount", appointmentService.countAppointments());
        model.addAttribute("userCount", userService.countUsers());
        model.addAttribute("recentAppointments", appointmentService.getRecentAppointments());
        return "admin/dashboard";
    }
}

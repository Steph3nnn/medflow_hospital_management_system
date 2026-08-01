package com.ochai.medflow.appointment.controller;

import com.ochai.medflow.appointment.dto.CreateAppointmentRequest;
import com.ochai.medflow.appointment.service.AppointmentService;
import com.ochai.medflow.common.enums.AppointmentStatus;
import com.ochai.medflow.doctor.service.DoctorService;
import com.ochai.medflow.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @GetMapping
    public String appointments(@RequestParam(required = false) String search,
                               Model model) {

        model.addAttribute(
                "appointments",
                appointmentService.searchAppointments(search)
        );

        model.addAttribute("search", search);

        return "admin/appointments";
    }

    @GetMapping("/create")
    public String createAppointmentForm(Model model) {

        model.addAttribute(
                "appointment",
                new CreateAppointmentRequest()
        );

        model.addAttribute(
                "patients",
                patientService.findAll()
        );

        model.addAttribute(
                "doctors",
                doctorService.findAll()
        );

        return "admin/create-appointment";
    }

    @PostMapping("/create")
    public String createAppointment(
            @Valid @ModelAttribute("appointment") CreateAppointmentRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("patients", patientService.findAll());
            model.addAttribute("doctors", doctorService.findAll());

            return "admin/create-appointment";
        }

        appointmentService.createAppointment(request);

        redirectAttributes.addFlashAttribute(
                "success",
                "Appointment created successfully!"
        );

        return "redirect:/admin/appointments";
    }

    @GetMapping("/edit/{id}")
    public String editAppointmentForm(@PathVariable Long id,
                                      Model model) {

        model.addAttribute(
                "appointment",
                appointmentService.getAppointmentById(id)
        );

        model.addAttribute("patients", patientService.findAll());
        model.addAttribute("doctors", doctorService.findAll());

        return "admin/edit-appointment";
    }

    @PostMapping("/edit/{id}")
    public String updateAppointment(
            @PathVariable Long id,
            @Valid @ModelAttribute("appointment") CreateAppointmentRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("patients", patientService.findAll());
            model.addAttribute("doctors", doctorService.findAll());

            return "admin/edit-appointment";
        }

        appointmentService.updateAppointment(id, request);

        redirectAttributes.addFlashAttribute(
                "success",
                "Appointment updated successfully!"
        );

        return "redirect:/admin/appointments";
    }

    @GetMapping("/{id}")
    public String viewAppointment(@PathVariable Long id,
                                  Model model) {

        model.addAttribute(
                "appointment",
                appointmentService.getAppointmentById(id)
        );

        model.addAttribute(
                "statuses",
                AppointmentStatus.values()
        );

        return "admin/view-appointment";
    }

    @PostMapping("/status/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam AppointmentStatus status,
                               RedirectAttributes redirectAttributes) {

        appointmentService.updateStatus(id, status);

        redirectAttributes.addFlashAttribute(
                "success",
                "Appointment status updated successfully!"
        );

        return "redirect:/admin/appointments";
    }

}

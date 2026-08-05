package com.ochai.medflow.consultation.controller;

import com.ochai.medflow.appointment.service.AppointmentService;
import com.ochai.medflow.consultation.dto.CreateConsultationRequest;
import com.ochai.medflow.consultation.service.ConsultationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;
    private final AppointmentService appointmentService;

    @GetMapping
    public String consultations(
            @RequestParam(required = false) String keyword,
            Model model) {

        model.addAttribute(
                "consultations",
                consultationService.search(keyword)
        );

        model.addAttribute("keyword", keyword);

        return "admin/consultations";

    }

    @GetMapping("/create")
    public String createConsultation(Model model) {

        model.addAttribute(
                "createConsultationRequest",
                new CreateConsultationRequest()
        );

        model.addAttribute(
                "appointments",
                appointmentService.findAll()
        );

        return "admin/create-consultations";

    }

    @PostMapping("/create")
    public String saveConsultation(

            @Valid
            @ModelAttribute CreateConsultationRequest createConsultationRequest,

            BindingResult bindingResult,

            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute(
                    "appointments",
                    appointmentService.findAll()
            );

            return "admin/create-consultations";

        }

        consultationService.createConsultation(createConsultationRequest);

        return "redirect:/admin/consultations";

    }

    @GetMapping("/view/{id}")
    public String viewConsultation(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "consultation",
                consultationService.findById(id)
        );

        return "admin/view-consultations";

    }

    @GetMapping("/edit/{id}")
    public String editConsultation(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "consultation",
                consultationService.findById(id));

        model.addAttribute(
                "appointments",
                appointmentService.findAll());

        return "admin/edit-consultations";

    }

    @PostMapping("/edit/{id}")
    public String updateConsultation(

            @PathVariable Long id,

            @Valid
            @ModelAttribute CreateConsultationRequest request,

            BindingResult bindingResult,

            Model model) {

        if(bindingResult.hasErrors()){

            model.addAttribute(
                    "appointments",
                    appointmentService.findAll());

            return "admin/edit-consultations";

        }

        consultationService.updateConsultation(id, request);

        return "redirect:/admin/consultations";

    }

    @PostMapping("/delete/{id}")
    public String deleteConsultation(
            @PathVariable Long id) {

        consultationService.delete(id);

        return "redirect:/admin/consultations";

    }
}
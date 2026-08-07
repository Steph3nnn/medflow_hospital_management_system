package com.ochai.medflow.prescription.controller;

import com.ochai.medflow.consultation.service.ConsultationService;
import com.ochai.medflow.prescription.dto.CreatePrescriptionRequest;
import com.ochai.medflow.prescription.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final ConsultationService consultationService;

    @GetMapping
    public String prescriptions(
            @RequestParam(required = false) String keyword,
            Model model) {

        model.addAttribute(
                "prescriptions",
                prescriptionService.search(keyword));

        model.addAttribute(
                "keyword",
                keyword);

        return "admin/prescription";
    }

    @GetMapping("/create")
    public String createPrescription(Model model) {

        model.addAttribute(
                "createPrescriptionRequest",
                new CreatePrescriptionRequest());

        model.addAttribute(
                "consultations",
                consultationService.findAll());

        return "admin/create-prescription";
    }

    @PostMapping("/create")
    public String savePrescription(

            @Valid
            @ModelAttribute CreatePrescriptionRequest request,

            BindingResult bindingResult,

            Model model) {



        prescriptionService.createPrescription(request);

        return "redirect:/admin/prescription";
    }

    @GetMapping("/view/{id}")
    public String viewPrescription(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "prescription",
                prescriptionService.findById(id));

        return "admin/view-prescription";
    }

    @GetMapping("/edit/{id}")
    public String editPrescription(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "prescription",
                prescriptionService.findById(id));

        model.addAttribute(
                "consultations",
                consultationService.findAll());

        return "admin/edit-prescription";
    }

    @PostMapping("/edit/{id}")
    public String updatePrescription(

            @PathVariable Long id,

            @Valid
            @ModelAttribute CreatePrescriptionRequest request,

            BindingResult bindingResult,

            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute(
                    "consultations",
                    consultationService.findAll());

            return "redirect:/edit-prescription";
        }

        prescriptionService.updatePrescription(id, request);

        return "redirect:/admin/prescription";
    }

    @PostMapping("/delete/{id}")
    public String deletePrescription(
            @PathVariable Long id) {

        prescriptionService.delete(id);

        return "redirect:/admin/prescriptions";
    }

}
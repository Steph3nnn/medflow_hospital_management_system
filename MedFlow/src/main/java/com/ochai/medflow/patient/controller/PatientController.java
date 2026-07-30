package com.ochai.medflow.patient.controller;

import com.ochai.medflow.common.enums.BloodGroup;
import com.ochai.medflow.common.enums.Gender;
import com.ochai.medflow.common.enums.Genotype;
import com.ochai.medflow.patient.dto.CreatePatientRequest;
import com.ochai.medflow.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public String patients(@RequestParam(required = false) String search,
                           Model model) {

        model.addAttribute("patients", patientService.searchPatients(search));
        model.addAttribute("search", search);

        return "admin/patients";
    }

    @GetMapping("/{id}")
    public String viewPatient(@PathVariable Long id,
                              Model model) {

        model.addAttribute(
                "patient",
                patientService.getPatientById(id)
        );

        return "admin/view-patient";
    }



    @GetMapping("/create")
    public String createPatientForm(Model model) {

        model.addAttribute("patient", new CreatePatientRequest());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("bloodGroups", BloodGroup.values());
        model.addAttribute("genotypes", Genotype.values());

        return "admin/create-patient";
    }

    @GetMapping("/edit/{id}")
    public String editPatientForm(@PathVariable Long id, Model model) {

        model.addAttribute("patient", patientService.getPatientById(id));
        model.addAttribute("genders", Gender.values());
        model.addAttribute("bloodGroups", BloodGroup.values());
        model.addAttribute("genotypes", Genotype.values());

        return "admin/edit-patient";
    }

    @PostMapping("/edit/{id}")
    public String updatePatient(
            @PathVariable Long id,
            @Valid @ModelAttribute("patient") CreatePatientRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("genders", Gender.values());
            model.addAttribute("bloodGroups", BloodGroup.values());
            model.addAttribute("genotypes", Genotype.values());

            return "redirect:/admin/patients";
        }

        patientService.updatePatient(id, request);

        redirectAttributes.addFlashAttribute(
                "success",
                "Patient updated successfully!"
        );

        return "redirect:/admin/patients";
    }

    @PostMapping("/activate/{id}")
    public String activatePatient(@PathVariable Long id,
                                  RedirectAttributes redirectAttributes) {

        patientService.activatePatient(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Patient activated successfully!"
        );

        return "redirect:/admin/patients";
    }

    @PostMapping("/deactivate/{id}")
    public String deactivatePatient(@PathVariable Long id,
                                    RedirectAttributes redirectAttributes) {

        patientService.deactivatePatient(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Patient deactivated successfully!"
        );

        return "redirect:/admin/patients";
    }

    @PostMapping("/create")
    public String createPatient(
            @Valid @ModelAttribute("patient") CreatePatientRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("genders", Gender.values());
            model.addAttribute("bloodGroups", BloodGroup.values());
            model.addAttribute("genotypes", Genotype.values());

            return "admin/create-patient";
        }

        patientService.createPatient(request);

        redirectAttributes.addFlashAttribute(
                "success",
                "Patient registered successfully!"
        );

        return "redirect:/admin/patients";
    }

}
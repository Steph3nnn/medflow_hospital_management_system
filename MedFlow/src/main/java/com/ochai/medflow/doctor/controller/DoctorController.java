package com.ochai.medflow.doctor.controller;

import com.ochai.medflow.common.enums.Department;
import com.ochai.medflow.common.enums.Gender;
import com.ochai.medflow.common.enums.Specialty;
import com.ochai.medflow.doctor.dto.CreateDoctorRequest;
import com.ochai.medflow.doctor.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public String doctors(@RequestParam(required = false) String search,
                          Model model) {

        model.addAttribute("doctors", doctorService.searchDoctors(search));
        model.addAttribute("search", search);

        return "admin/doctors";
    }

    @GetMapping("/create")
    public String createDoctorForm(Model model) {

        model.addAttribute("doctor", new CreateDoctorRequest());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("departments", Department.values());
        model.addAttribute("specialties", Specialty.values());

        return "admin/create-doctor";
    }

    @PostMapping("/create")
    public String createDoctor(
            @Valid @ModelAttribute("doctor") CreateDoctorRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("genders", Gender.values());
            model.addAttribute("departments", Department.values());
            model.addAttribute("specialties", Specialty.values());

            return "admin/create-doctor";
        }

        doctorService.createDoctor(request);

        redirectAttributes.addFlashAttribute(
                "success",
                "Doctor registered successfully!"
        );

        return "redirect:/admin/doctors";
    }

    @GetMapping("/edit/{id}")
    public String editDoctorForm(@PathVariable Long id,
                                 Model model) {

        model.addAttribute("doctor", doctorService.getDoctorById(id));
        model.addAttribute("genders", Gender.values());
        model.addAttribute("departments", Department.values());
        model.addAttribute("specialties", Specialty.values());

        return "admin/edit-doctor";
    }

    @PostMapping("/edit/{id}")
    public String updateDoctor(
            @PathVariable Long id,
            @Valid @ModelAttribute("doctor") CreateDoctorRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("genders", Gender.values());
            model.addAttribute("departments", Department.values());
            model.addAttribute("specialties", Specialty.values());

            return "admin/edit-doctor";
        }

        doctorService.updateDoctor(id, request);

        redirectAttributes.addFlashAttribute(
                "success",
                "Doctor updated successfully!"
        );

        return "redirect:/admin/doctors";
    }

    @GetMapping("/{id}")
    public String viewDoctor(@PathVariable Long id,
                             Model model) {

        model.addAttribute(
                "doctor",
                doctorService.getDoctorById(id)
        );

        return "admin/view-doctor";
    }

    @PostMapping("/activate/{id}")
    public String activateDoctor(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {

        doctorService.activateDoctor(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Doctor activated successfully!"
        );

        return "redirect:/admin/doctors";
    }

    @PostMapping("/deactivate/{id}")
    public String deactivateDoctor(@PathVariable Long id,
                                   RedirectAttributes redirectAttributes) {

        doctorService.deactivateDoctor(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Doctor deactivated successfully!"
        );

        return "redirect:/admin/doctors";
    }

}
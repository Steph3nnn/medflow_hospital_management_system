package com.ochai.medflow.pharmacy.controller;

import com.ochai.medflow.pharmacy.dto.CreatePharmacyRequest;
import com.ochai.medflow.pharmacy.service.PharmacyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/pharmacy")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @GetMapping
    public String pharmacy(
            @RequestParam(required = false) String keyword,
            Model model) {

        model.addAttribute(
                "medications",
                pharmacyService.search(keyword)
        );

        model.addAttribute("keyword", keyword);

        return "admin/pharmacy";
    }

    @GetMapping("/create")
    public String createPharmacy(Model model) {

        model.addAttribute(
                "createPharmacyRequest",
                new CreatePharmacyRequest()
        );

        return "admin/create-pharmacy";
    }

    @PostMapping("/create")
    public String savePharmacy(
            @Valid
            @ModelAttribute CreatePharmacyRequest request,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/create-pharmacy";
        }

        pharmacyService.createMedication(request);

        return "redirect:/admin/pharmacy";
    }

    @GetMapping("/view/{id}")
    public String viewPharmacy(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "pharmacy",
                pharmacyService.findById(id)
        );

        return "admin/view-pharmacy";
    }

    @GetMapping("/edit/{id}")
    public String editPharmacy(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "pharmacy",
                pharmacyService.findById(id)
        );

        return "admin/edit-pharmacy";
    }

    @PostMapping("/edit/{id}")
    public String updatePharmacy(
            @PathVariable Long id,

            @Valid
            @ModelAttribute CreatePharmacyRequest request,

            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/edit-pharmacy";
        }

        pharmacyService.updateMedication(id, request);

        return "redirect:/admin/pharmacy";
    }

    @PostMapping("/delete/{id}")
    public String deletePharmacy(
            @PathVariable Long id) {

        pharmacyService.deleteMedication(id);

        return "redirect:/admin/pharmacy";
    }
}
package com.ochai.medflow.billing.controller;

import com.ochai.medflow.appointment.service.AppointmentService;
import com.ochai.medflow.billing.dto.CreateBillingRequest;
import com.ochai.medflow.billing.service.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/billing")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;
    private final AppointmentService appointmentService;

    @GetMapping
    public String billing(
            @RequestParam(required = false) String keyword,
            Model model) {

        model.addAttribute(
                "billings",
                billingService.search(keyword)
        );

        model.addAttribute("keyword", keyword);

        return "admin/billing";
    }

    @GetMapping("/create")
    public String createBilling(Model model) {

        model.addAttribute(
                "createBillingRequest",
                new CreateBillingRequest()
        );

        model.addAttribute(
                "appointments",
                appointmentService.findAll()
        );

        return "admin/create-billing";
    }

    @PostMapping("/create")
    public String saveBilling(
            @Valid
            @ModelAttribute CreateBillingRequest request,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute(
                    "appointments",
                    appointmentService.findAll()
            );

            return "admin/create-billing";
        }

        billingService.createBilling(request);

        return "redirect:/admin/billing";
    }

    @GetMapping("/view/{id}")
    public String viewBilling(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "billing",
                billingService.findById(id)
        );

        return "admin/view-billing";
    }

    @GetMapping("/edit/{id}")
    public String editBilling(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "billing",
                billingService.findById(id)
        );

        model.addAttribute(
                "appointments",
                appointmentService.findAll()
        );

        return "admin/edit-billing";
    }

    @PostMapping("/edit/{id}")
    public String updateBilling(
            @PathVariable Long id,
            @Valid
            @ModelAttribute CreateBillingRequest request,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute(
                    "appointments",
                    appointmentService.findAll()
            );

            return "admin/edit-billing";
        }

        billingService.updateBilling(id, request);

        return "redirect:/admin/billing";
    }

    @PostMapping("/delete/{id}")
    public String deleteBilling(
            @PathVariable Long id) {

        billingService.delete(id);

        return "redirect:/admin/billing";
    }
}
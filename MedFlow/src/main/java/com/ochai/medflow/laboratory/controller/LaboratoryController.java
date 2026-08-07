package com.ochai.medflow.laboratory.controller;

import com.ochai.medflow.consultation.service.ConsultationService;
import com.ochai.medflow.laboratory.dto.CreateLaboratoryRequest;
import com.ochai.medflow.laboratory.service.LaboratoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/laboratories")
@RequiredArgsConstructor
public class LaboratoryController {

    private final LaboratoryService laboratoryService;
    private final ConsultationService consultationService;

    @GetMapping
    public String laboratories(@RequestParam(required = false) String keyword,
                               Model model){

        model.addAttribute("laboratories",
                laboratoryService.search(keyword));

        model.addAttribute("keyword",keyword);

        return "admin/laboratories";
    }

    @GetMapping("/create")
    public String create(Model model){

        model.addAttribute("createLaboratoryRequest",
                new CreateLaboratoryRequest());

        model.addAttribute("consultations",
                consultationService.findAll());

        return "admin/create-laboratory";
    }

    @PostMapping("/create")
    public String save(@Valid
                       @ModelAttribute CreateLaboratoryRequest request,
                       BindingResult result,
                       Model model){

        if(result.hasErrors()){

            model.addAttribute("consultations",
                    consultationService.findAll());

            return "admin/create-laboratory";
        }

        laboratoryService.createLaboratory(request);

        return "redirect:/admin/laboratories";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id,
                       Model model){

        model.addAttribute("laboratory",
                laboratoryService.findById(id));

        return "admin/view-laboratory";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       Model model){

        model.addAttribute("laboratory",
                laboratoryService.findById(id));

        model.addAttribute("consultations",
                consultationService.findAll());

        return "admin/edit-laboratory";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute CreateLaboratoryRequest request){

        laboratoryService.updateLaboratory(id,request);

        return "redirect:/admin/laboratories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){

        laboratoryService.delete(id);

        return "redirect:/admin/laboratories";
    }

}
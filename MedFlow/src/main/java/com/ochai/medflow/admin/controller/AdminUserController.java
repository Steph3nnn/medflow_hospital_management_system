package com.ochai.medflow.admin.controller;


import com.ochai.medflow.admin.dto.CreateUserRequest;
import com.ochai.medflow.authentication.service.UserService;
import com.ochai.medflow.common.enums.RoleName;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public String users(Model model) {

        model.addAttribute("users", userService.findAll());

        return "admin/users";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {

        model.addAttribute("user", new CreateUserRequest());
        model.addAttribute("roles", RoleName.values());

        return "admin/create-user";
    }

    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute("user") CreateUserRequest request,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "admin/create-user";
        }

        userService.createUser(request);

        redirectAttributes.addFlashAttribute(
                "success",
                "User created successfully!"
        );

        return "redirect:/admin/users";
    }

}
package com.ochai.medflow.admin.controller;


import com.ochai.medflow.admin.dto.CreateUserRequest;
import com.ochai.medflow.authentication.entity.User;
import com.ochai.medflow.authentication.service.UserService;
import com.ochai.medflow.common.enums.RoleName;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {

        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", RoleName.values());

        return "admin/edit-user";
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

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute User user) {

        userService.updateUser(id, user);

        return "redirect:/admin/users";
    }

}
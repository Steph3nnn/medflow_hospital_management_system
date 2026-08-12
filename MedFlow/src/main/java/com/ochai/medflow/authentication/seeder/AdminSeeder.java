package com.ochai.medflow.authentication.seeder;

import com.ochai.medflow.authentication.entity.Role;
import com.ochai.medflow.authentication.entity.User;
import com.ochai.medflow.authentication.repository.RoleRepository;
import com.ochai.medflow.authentication.repository.UserRepository;
import com.ochai.medflow.common.enums.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(2)
public class AdminSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {

        if (userRepository.existsByEmail("admin@medflow.com")) {
            return;
        }

        Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        User admin = User.builder()
                .firstName("System")
                .lastName("Administrator")
                .email("admin@medflow.com")
                .password(passwordEncoder.encode("Admin@123"))
                .role(adminRole)
                .enabled(true)
                .build();

        userRepository.save(admin);

        System.out.println("Default admin account created.");
    }
}
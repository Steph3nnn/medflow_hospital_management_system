package com.ochai.medflow.authentication.seeder;

import com.ochai.medflow.authentication.entity.Role;
import com.ochai.medflow.authentication.repository.RoleRepository;
import com.ochai.medflow.common.enums.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) {

        for (RoleName roleName : RoleName.values()) {

            if (roleRepository.findByName(roleName).isEmpty()) {

                Role role = Role.builder()
                        .name(roleName)
                        .build();

                roleRepository.save(role);
            }
        }

        System.out.println("Roles seeded successfully.");
    }
}
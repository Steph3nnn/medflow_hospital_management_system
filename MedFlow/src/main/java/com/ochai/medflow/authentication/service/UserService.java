package com.ochai.medflow.authentication.service;

import com.ochai.medflow.admin.dto.CreateUserRequest;
import com.ochai.medflow.authentication.entity.Role;
import com.ochai.medflow.authentication.entity.User;
import com.ochai.medflow.authentication.repository.RoleRepository;
import com.ochai.medflow.authentication.repository.UserRepository;
import com.ochai.medflow.common.enums.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void updateUser(Long id, User updatedUser) {

        User user = getUserById(id);

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());

        userRepository.save(user);
    }

    public void createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Role role = roleRepository.findByName(RoleName.valueOf(request.getRole()))
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .enabled(true)
                .build();

        userRepository.save(user);
    }


}

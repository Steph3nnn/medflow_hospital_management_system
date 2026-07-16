package com.ochai.medflow.authentication.service;

import com.ochai.medflow.authentication.entity.Role;
import com.ochai.medflow.authentication.repository.RoleRepository;
import com.ochai.medflow.common.enums.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public Role getRole(RoleName roleName){
        return roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));
    }
}

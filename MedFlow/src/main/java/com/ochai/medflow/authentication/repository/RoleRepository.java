package com.ochai.medflow.authentication.repository;

import com.ochai.medflow.authentication.entity.Role;
import com.ochai.medflow.common.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}

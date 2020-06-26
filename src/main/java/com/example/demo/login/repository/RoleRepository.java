package com.example.demo.login.repository;

import com.example.demo.login.domain.Role;
import com.example.demo.login.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}

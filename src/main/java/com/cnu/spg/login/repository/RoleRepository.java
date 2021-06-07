package com.cnu.spg.login.repository;

import com.cnu.spg.login.domain.Role;
import com.cnu.spg.login.domain.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}

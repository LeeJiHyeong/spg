package com.cnu.spg.repository.user;

import com.cnu.spg.domain.login.Role;
import com.cnu.spg.domain.login.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}

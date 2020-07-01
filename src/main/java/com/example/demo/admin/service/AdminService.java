package com.example.demo.admin.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.login.domain.Role;
import com.example.demo.login.domain.RoleName;
import com.example.demo.login.domain.User;
import com.example.demo.login.repository.RoleRepository;
import com.example.demo.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // change user autheticated roleName : ROLE_STUDENT -> student, ROLE_ADMIN -> admin
    @Transactional(rollbackOn = Exception.class)
    public void changeUserAuthenticated(User user, RoleName roleName) {
        Role studentUser = this.roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName));
        user.setRoles(Collections.singleton(studentUser));

        this.userRepository.save(user);
    }
}
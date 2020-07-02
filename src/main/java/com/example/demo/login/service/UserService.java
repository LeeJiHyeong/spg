package com.example.demo.login.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.login.domain.Role;
import com.example.demo.login.domain.RoleName;
import com.example.demo.login.domain.User;
import com.example.demo.login.repository.RoleRepository;
import com.example.demo.login.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.RollbackException;
import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(rollbackOn = RollbackException.class)
    public boolean save(User user) {
        if (this.userRepository.existsByUserName(user.getUserName())) {
            return false;
        }

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        Role unAuthUserRole = this.roleRepository.findByName(RoleName.ROLE_UNAUTH)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "roleName", RoleName.ROLE_UNAUTH));
        user.setRoles(Collections.singleton(unAuthUserRole));
        this.userRepository.save(user);

        return true;
    }

    @Transactional(rollbackOn = RollbackException.class)
    public User findByUserName(String userName) {
        return this.userRepository.findByUserName(userName)
                .orElse(null);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteByUserName(String username) {
        this.userRepository.deleteByUserName(username);
    }

    @Transactional(rollbackOn = Exception.class)
    public User changeUserPassword(String username, String passowrd) {
        User oridinaryUser = this.userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        oridinaryUser.setPassword(this.bCryptPasswordEncoder.encode(passowrd));

        return this.userRepository.save(oridinaryUser);
    }
}

package com.example.demo.login.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.login.domain.Role;
import com.example.demo.login.domain.RoleName;
import com.example.demo.login.domain.User;
import com.example.demo.login.repository.RoleRepository;
import com.example.demo.login.repository.UserRepository;
import com.example.demo.login.request.ChangingPasswordRequest;
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

    @Transactional
    public User changeUserPassword(String username, ChangingPasswordRequest changingPasswordRequest) {
        User oridinaryUser = this.userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        boolean isUserPasswordCorrect = this.bCryptPasswordEncoder.matches(changingPasswordRequest.getBeforePassword()
                , oridinaryUser.getPassword());
        if (!isUserPasswordCorrect) {
            return null;
        }
        oridinaryUser.setPassword(this.bCryptPasswordEncoder.encode(changingPasswordRequest.getPassword()));

        return this.userRepository.save(oridinaryUser);
    }

    @Transactional
    public boolean checkNowPassword(String username, String passowrd) {
        User user = this.userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        return this.bCryptPasswordEncoder.matches(passowrd, user.getPassword());
    }

    @Transactional
    public User updateUsernameAndName(String pastUserName, String username, String name) {
        User user = this.userRepository.findByUserName(pastUserName)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        user.setUserName(username);
        user.setName(name);

        return this.userRepository.save(user);
    }
}

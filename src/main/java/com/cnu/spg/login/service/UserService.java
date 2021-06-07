package com.cnu.spg.login.service;

import com.cnu.spg.login.domain.Role;
import com.cnu.spg.login.domain.RoleName;
import com.cnu.spg.login.domain.User;
import com.cnu.spg.login.repository.RoleRepository;
import com.cnu.spg.login.repository.UserRepository;
import com.cnu.spg.exception.ResourceNotFoundException;
import com.cnu.spg.login.request.ChangingPasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
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

    public User findByUserName(String userName) {
        return this.userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user name", userName));
    }

    @Transactional
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

        return oridinaryUser;
    }

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

        return user;
    }
}
package com.example.demo.admin.service;

import com.example.demo.admin.reponse.ReponseUserData;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.login.domain.Role;
import com.example.demo.login.domain.RoleName;
import com.example.demo.login.domain.User;
import com.example.demo.login.repository.RoleRepository;
import com.example.demo.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public boolean changeUserAuthenticated(Long userId, String username, Set<Role> roles) {
        User user = this.userRepository.getOne(userId);
        if (user.getUserName().equals(username)) {
            if (user.getRoles().contains(new Role(RoleName.ROLE_UNAUTH))) {
                user.setActiveDate(Calendar.getInstance());
            }
            Set<Role> newRoles = roles.stream().map(role -> this.roleRepository.findByName(role.getName())
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roles))).collect(Collectors.toSet());
            user.setRoles(newRoles);

            this.userRepository.save(user);

            return true;
        }

        return false;
    }

    @Transactional
    public List<ReponseUserData> findUsersByPage(int startPageIndex) {
        Pageable pageable = PageRequest.of(startPageIndex, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<User> userPage = this.userRepository.findAll(pageable);
        List<User> userList = userPage.getContent();

        return userList.stream().map(ReponseUserData::new).collect(Collectors.toList());
    }

    @Transactional
    public long getTotalCount() {
        return this.userRepository.count();
    }

    @Transactional
    public boolean deleteUserData(Long userId, String username) {
        User user = this.userRepository.getOne(userId);
        if (user.getUserName().equals(username)) {
            this.userRepository.deleteById(userId);

            return true;
        }

        return false;
    }
}
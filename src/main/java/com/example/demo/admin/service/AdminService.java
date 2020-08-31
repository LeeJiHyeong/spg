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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public boolean changeUserAuthenticated(Long userId, String username, RoleName roleName) {
        User user = this.userRepository.getOne(userId);
        if (user.getUserName().equals(username)) {
            if (user.getRoles().contains(new Role(RoleName.ROLE_UNAUTH))) {
                user.setActiveDate(Calendar.getInstance());
            }

            Role studentRole = this.roleRepository.findByName(roleName)
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName));
            user.setRoles(Collections.singleton(studentRole));

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
        List<ReponseUserData> resultUserData = new ArrayList<>();
        userList.forEach(user -> resultUserData.add(new ReponseUserData(user)));

        return resultUserData;
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
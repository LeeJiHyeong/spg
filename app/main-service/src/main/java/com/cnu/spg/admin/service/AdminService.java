package com.cnu.spg.admin.service;

import com.cnu.spg.admin.reponse.ReponseUserData;
import com.cnu.spg.domain.login.Role;
import com.cnu.spg.domain.login.RoleName;
import com.cnu.spg.domain.login.User;
import com.cnu.spg.exception.ResourceNotFoundException;
import com.cnu.spg.repository.user.RoleRepository;
import com.cnu.spg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public boolean changeUserAuthenticated(Long userId, String username, Set<Role> roles) {
        User user = this.userRepository.getOne(userId);
        if (user.getUsername().equals(username)) {
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
        if (user.getUsername().equals(username)) {
            this.userRepository.deleteById(userId);

            return true;
        }

        return false;
    }
}
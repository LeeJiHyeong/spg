package com.example.demo.login.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.login.domain.User;
import com.example.demo.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.RollbackException;
import javax.transaction.Transactional;

@Component
public class LoginService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(rollbackOn = RollbackException.class)
    public UserDetails loadUserByUsername(String userName) {
        User user = this.userRepository.findByUserName(userName)
                .orElseThrow(
                        () -> new UsernameNotFoundException("I can not found user data" + userName)
                );
        return UserPrincipal.create(user);
    }

    @Transactional(rollbackOn = RollbackException.class)
    public UserDetails loadUserById(Long id) {
        User user = this.userRepository.findById(id).orElseThrow( // get the id
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user); // make user principal
    }
}

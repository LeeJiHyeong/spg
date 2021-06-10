package com.cnu.spg.login.service;

import com.cnu.spg.exception.ResourceNotFoundException;
import com.cnu.spg.login.domain.User;
import com.cnu.spg.login.domain.UserPrincipal;
import com.cnu.spg.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user = this.userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("I can not found user data" + userName));

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = this.userRepository.findById(id).orElseThrow( // get the id
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user); // make user principal
    }
}

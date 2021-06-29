package com.cnu.spg.config.local;

import com.cnu.spg.domain.login.Role;
import com.cnu.spg.domain.login.RoleName;
import com.cnu.spg.domain.login.User;
import com.cnu.spg.repository.user.RoleRepository;
import com.cnu.spg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@Profile("local")
@RequiredArgsConstructor
public class LocalConfig {

    private final InitService initService;

    @PostConstruct
    public void postContructorFunction() {
        initService.init();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    private static class InitService {
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;

        public void init() {
            Role admin = new Role(RoleName.ROLE_ADMIN);
            Role student = new Role(RoleName.ROLE_STUDENT);
            Role unAuth = new Role(RoleName.ROLE_UNAUTH);

            roleRepository.save(admin);
            roleRepository.save(student);
            roleRepository.save(unAuth);
            String password = "fun123";

            User john = User.createUser("john", "john", passwordEncoder.encode(password), admin);
            User susan = User.createUser("susan", "susan", passwordEncoder.encode(password), unAuth);
            User amanda = User.createUser("amanda", "amanda", passwordEncoder.encode(password), admin, student);

            userRepository.save(john);
            userRepository.save(susan);
            userRepository.save(amanda);
        }
    }
}

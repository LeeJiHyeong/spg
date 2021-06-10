package com.cnu.spg;

import com.cnu.spg.admin.service.AdminService;
import com.cnu.spg.login.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminTest {

    @Autowired
    AdminService adminService;

    @Autowired
    UserRepository userRepository;

    @Test
    void changeAuthTest() throws Exception {
//        // given
//        User user = this.userRepository.findByUserName("john").orElseThrow(Exception::new);
//
//        // when
//        this.adminService.changeUserAuthenticated(user, RoleName.ROLE_STUDENT);
//
//        // then
//        User changedAuthUser = this.userRepository.findByUserName("john").orElseThrow(Exception::new);
//        boolean result = changedAuthUser.getRoles().contains("ROLE_STUDENT");
//
//        assertThat(result, is(true));
    }
}
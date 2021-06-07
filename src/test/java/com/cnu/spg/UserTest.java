package com.cnu.spg;

import com.cnu.spg.login.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    void matchUserPasswordTest() {
        // given
        String userName = "john";
        String password = "fun123";

        // when
        boolean result = this.userService.checkNowPassword(userName, password);

        // then
        assertThat(result, is(true));
    }
}

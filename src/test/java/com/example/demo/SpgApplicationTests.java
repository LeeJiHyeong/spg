package com.example.demo;

import com.example.demo.login.domain.User;
import com.example.demo.login.request.ChangingPasswordRequest;
import com.example.demo.login.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SpgApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Test
    public void loginTest() throws Exception {
        this.mockMvc.perform(get("/login/SignInPage"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("_csrf")))
                .andDo(print());
    }

    @Test
    public void failLogin() throws Exception {
        this.mockMvc.perform(post("/authenticateTheUser")
                .param("username", "mary")
                .param("password", "fun123")
        )
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    public void changeUserPasswordTest() throws Exception {
        // given
        String userId = "john";
        ChangingPasswordRequest changingPasswordRequest = new ChangingPasswordRequest();
        changingPasswordRequest.setBeforePassword("fun123");
        changingPasswordRequest.setPassword("abc123");

        // when
        User result = userService.changeUserPassword(userId, changingPasswordRequest);

        // then
        Assert.assertThat(result, is(notNullValue()));
    }

    @Test
    public void deleteUserTest() throws Exception {
        // given
        String username = "mary";

        // when
        this.userService.deleteByUserName(username);
    }
}

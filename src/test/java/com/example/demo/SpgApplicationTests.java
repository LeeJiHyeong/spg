package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SpgApplicationTests {

    @Autowired
    MockMvc mockMvc;

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
}

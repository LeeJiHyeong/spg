package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterController {

    @RequestMapping(value = "SignUpPage")
    public String registerPage() {
        return "register.html";
    }
}

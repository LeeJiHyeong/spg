package com.cnu.spg.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    
    @RequestMapping(value = "signInPage")
    public String goTestLogin() {
        return "login.html";
    }
}

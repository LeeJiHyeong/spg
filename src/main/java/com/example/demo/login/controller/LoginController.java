package com.example.demo.login.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "SignInPage")
    public String goTestLogin() {

        return "login-test.html";
    }

//    @RequestMapping(value = "signin", method = RequestMethod.POST)
//    public String doTestLogin(@Valid @RequestBody LoginRequest loginRequest) {
//        Authentication authentication = this.authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getUsername(),
//                        loginRequest.getPassword()
//                )
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = this.tokenProvider.makeToken(authentication);
//        // jwt 를 main page에 넘기기
//        return "redirect:/..";
//    }
}

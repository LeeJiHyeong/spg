package com.example.demo.login.controller;

import com.example.demo.login.request.ChangingPasswordRequest;
import com.example.demo.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/deleteUser")
    public String deleteUser(HttpSession httpSession) {
        Object id = httpSession.getAttribute("userName");
        if (id == null) {
            return "redirect:/";
        }
        this.userService.deleteByUserName((String) id);

        return "redirect:/";
    }

    @PostMapping("/changePassword")
    public String changeUserPassowrd(@RequestBody ChangingPasswordRequest changingPasswordRequest) {
        if (this.userService.changeUserPassword(changingPasswordRequest.getUserName(), changingPasswordRequest.getPassword()) == null) {
            return ""; // 비밀번호 변경 실패
        }

        return ""; // 성공
    }
}

package com.example.demo.login.controller;

import com.example.demo.login.domain.User;
import com.example.demo.login.request.ChangingPasswordRequest;
import com.example.demo.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @RequestMapping(value = "management")
    public String Management(HttpSession session, Model model) {
//        if (session.getAttribute("userName") != null) {
//            String userName = (String) session.getAttribute("userName");
//            ChangingPasswordRequest changingPasswordRequest = new ChangingPasswordRequest();
//        }

        return "user-management.html";
    }

    @RequestMapping(value = "/identity", method = RequestMethod.POST)
    public String userIdentity(HttpSession session, @RequestParam("inputPW") String inputpw, Model model) {

        if (session.getAttribute("userName") != null) {

            User user = userService.findByUserName((String) session.getAttribute("userName"));
            String disabled = null;
            String placeholder = null;
            String userName = null;
            String inputPW_Dot = "";

            System.out.println(inputpw);
            System.out.println(inputpw.length());

            for (int i = 0; i < inputpw.length(); i++) {
                inputPW_Dot += "●";
            }

            // add
            if (passwordEncoder.matches(inputpw, user.getPassword())) {
                disabled = "disabled";
                userName = user.getName();
                model.addAttribute("placeholder", placeholder);
                model.addAttribute("autofocus", "autofocus");
                model.addAttribute("inputPW", inputPW_Dot);
            } else {
                model.addAttribute("placeholder", "본인확인이 필요합니다.");
            }

            model.addAttribute("userId", user.getUserName());
            model.addAttribute("disabled", disabled);
            model.addAttribute("userName", userName);
        }

        return "user-management.html";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(HttpSession httpSession) {
        Object id = httpSession.getAttribute("userName");
        if (id == null) {
            return "redirect:/";
        }
        this.userService.deleteByUserName((String) id);

        return "redirect:/";
    }

    @PostMapping("/checkNowPassword")
    public String checkPassword(HttpSession httpSession,
                                @RequestParam("password") String password) {
        String userName = (String) httpSession.getAttribute("userName");
        if (userName == null) {
            return "";
        }
        String urlHeader = "redirect:";
        String urlFooter = this.userService.checkNowPassword(userName, password) ? "/user/passwordChangePage" : "/";

        return urlHeader + urlFooter;
    }

    @GetMapping("/passwordChangePage")
    public String passwordChangePage(Model model) {
        // todo add authetication for this page
        model.addAttribute("changingPasswordRequest", new ChangingPasswordRequest());
        return "user-password-change";
    }

    @PostMapping("/changePassword")
    public String changeUserPassowrd(HttpSession httpSession,
                                     @Valid @ModelAttribute("changingPasswordRequest") ChangingPasswordRequest changingPasswordRequest,
                                     BindingResult bindingResult,
                                     Model model) {
        String userName = (String) httpSession.getAttribute("userName");
        if (bindingResult.hasErrors()) {
            model.addAttribute("changeError", "The two passwords do not match.");
            model.addAttribute("changingPasswordRequest", new ChangingPasswordRequest());

            return "redirect:/user/passwordChangePage";
        }
        if (this.userService.changeUserPassword(userName, changingPasswordRequest.getPassword()) == null) {
            model.addAttribute("changeError", "password update error");
            model.addAttribute("changingPasswordRequest", new ChangingPasswordRequest());

            return "redirect:/user/passwordChangePage";
        }

        return "redirect:/";
    }
}

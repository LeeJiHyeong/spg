package com.example.demo.login.controller;

import com.example.demo.login.domain.RegisterRequest;
import com.example.demo.login.domain.User;
import com.example.demo.login.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @RequestMapping(value = "signUpPage")
    public String registerPage(Model model) {
        model.addAttribute("user", new RegisterRequest());
        return "register";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") RegisterRequest user,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        String username = user.getUserName();
        User exitUser = this.userService.findByUserName(username);
        User userForSave = new User(user.getName(), user.getUserName(), user.getPassword());
        if (exitUser != null || !this.userService.save(userForSave)) {
            model.addAttribute("user", new User());
            model.addAttribute("registrationError", "User name already exists.");

            this.logger.warn("User name already exists.");
            return "register";
        }

        this.logger.info("Successfully created user: " + username);

        return "redirect:/login/signInPage";
    }
}

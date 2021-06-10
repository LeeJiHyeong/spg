package com.cnu.spg.login.controller;

import com.cnu.spg.login.domain.User;
import com.cnu.spg.login.request.RegisterRequest;
import com.cnu.spg.login.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

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

            log.warn("User name already exists.");
            return "register";
        }

        log.info("Successfully created user: " + username);

        return "redirect:/login/signInPage";
    }

    @GetMapping("/registration")
    public String redirectRegisterPage() {
        return "redirect:/register/signUpPage";
    }
}

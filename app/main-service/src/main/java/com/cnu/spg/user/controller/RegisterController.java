package com.cnu.spg.user.controller;

import com.cnu.spg.dto.user.UserRegisterDto;
import com.cnu.spg.user.service.UserService;
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
        model.addAttribute("user", new UserRegisterDto());
        return "register";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") UserRegisterDto userRegisterDto,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        Long createdUserId = userService.signUp(userRegisterDto);
        log.info("Successfully created userId: " + createdUserId);

        return "redirect:/login/signInPage";
    }

    @GetMapping("/registration")
    public String redirectRegisterPage() {
        return "redirect:/register/signUpPage";
    }
}

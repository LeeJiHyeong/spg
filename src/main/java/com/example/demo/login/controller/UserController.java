package com.example.demo.login.controller;

import com.example.demo.login.domain.User;
import com.example.demo.login.request.ChangingPasswordRequest;
import com.example.demo.login.request.UserDataRequest;
import com.example.demo.login.service.UserPrincipal;
import com.example.demo.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/withdrawalPage")
    public String withdrawalPage(HttpSession session, Model model) {
        UserPrincipal user = (UserPrincipal) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("userName", user.getUsername());

        return "user-withdrawal";
    }

    @RequestMapping("/withdrawal")
    public String withdrawalUser(HttpSession session, @RequestParam("password") String password, HttpServletResponse response) throws IOException {

        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            return "index";
        }

        boolean checkNowUserPassword = this.userService.checkNowPassword(userName, password);

        if (checkNowUserPassword) {
            this.userService.deleteByUserName(userName); // delete user
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('회원 탈퇴가 완료되었습니다.');</script>");
            out.flush();
            session.invalidate();
            return "index";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('비밀번호가 일치하지 않습니다.');</script>");
            out.flush();
            return "user-withdrawal";
        }

    }

    @GetMapping("/management")
    public String management(HttpSession session, Model model) {
        UserPrincipal user = (UserPrincipal) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        UserDataRequest userDataRequest = new UserDataRequest(user.getUsername(), user.getName());
        model.addAttribute("userDataRequest", userDataRequest);
        model.addAttribute("userName", user.getUsername());

        return "user-management";
    }

    @PostMapping("/changeUserData") // change user id and name
    public String changeUserData(HttpSession httpSession,
                                 @Valid @ModelAttribute("userDataRequest") UserDataRequest userDataRequest,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("changeError", "Sorry please insert data.");

            return "user-management";
        }

        String pastUsername = (String) httpSession.getAttribute("userName");
        User user = this.userService.updateUsernameAndName(pastUsername, userDataRequest.getUserName(), userDataRequest.getName());

        if (user == null) {
            model.addAttribute("changeError", "Sorry It is not changed.");

            return "user-management";
        }

        return "redirect:/";
    }

    @GetMapping("/changeUserData")
    public String redirectmanagement() {
        return "redirect:/user/management";
    }

    @GetMapping("/passwordChangePage")
    public String passwordChangePage(Model model, HttpSession session) {
    	// session
        if (session.getAttribute("userName") != null) {
            String userName = (String) session.getAttribute("userName");
            model.addAttribute("userName", userName);
        }
    	
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
            model.addAttribute("changeError", "The two passwords do not match or please insert your password.");
            model.addAttribute("changingPasswordRequest", new ChangingPasswordRequest());

            return "user-password-change";
        }
        if (this.userService.changeUserPassword(userName, changingPasswordRequest) == null) {
            model.addAttribute("changeError", "you password is not correct.");
            model.addAttribute("changingPasswordRequest", new ChangingPasswordRequest());

            return "user-password-change";
        }

        return "redirect:/";
    }

    @GetMapping("/changePassword") // redirect to real page
    public String redirectPasswordChangePage() {
        return "redirect:/user/passwordChangePage";
    }
}

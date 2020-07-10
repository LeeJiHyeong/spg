package com.example.demo.login.controller;

import com.example.demo.login.domain.User;
import com.example.demo.login.request.ChangingPasswordRequest;
import com.example.demo.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//추가
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    
    
    @RequestMapping(value="Management")
    public String Management(HttpSession session, Model model) {
				
		String userId = null;
		
		if (session.getAttribute("userName") != null) {
			userId = (String)session.getAttribute("userName");
			model.addAttribute("userId", userId);
			model.addAttribute("placeholder", "본인확인이 필요합니다.");
		}				
		
		 return "User_Management.html";
    }
    
    @RequestMapping(value="/identity", method=RequestMethod.POST)
    public String userIdentity(HttpSession session, @RequestParam("inputPW")String inputpw, Model model) {

		if (session.getAttribute("userName") != null) {
			
	    	User user = userService.findByUserName((String)session.getAttribute("userName"));
	    	String disabled = null;
	    	String placeholder = null;
	    	String userName = null;
	    	String inputPW_Dot = "";

	    	System.out.println(inputpw);
	    	System.out.println(inputpw.length());   	

	    	for(int i = 0; i<inputpw.length(); i++) {
	    		inputPW_Dot += "●";
	    	}
	    	
	    	// add    	    	
	    	if(passwordEncoder.matches(inputpw, user.getPassword())) {
	    		disabled = "disabled";
	    		userName = user.getName();
	    		model.addAttribute("placeholder", placeholder);
	    		model.addAttribute("autofocus", "autofocus");
				model.addAttribute("inputPW", inputPW_Dot);
	    	}
	    	else {
				model.addAttribute("placeholder", "본인확인이 필요합니다.");
	    	}
	    	
			model.addAttribute("userId", user.getUserName());	    	
    		model.addAttribute("disabled", disabled);	
    		model.addAttribute("userName", userName);
		}
    	
    	return "User_Management.html";
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

    @PostMapping("/changePassword")
    public String changeUserPassowrd(@RequestBody ChangingPasswordRequest changingPasswordRequest) {    
        if (this.userService.changeUserPassword(changingPasswordRequest.getUserName(), changingPasswordRequest.getPassword()) == null) {
            return ""; // 비밀번호 변경 실패
        }

        return ""; // 성공
    }
}

package com.example.demo.login.controller;

import java.lang.ProcessBuilder.Redirect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value = "SignInPage")
	public String goTestLogin() {
		
		return "login-test.html";
	}
	
	@RequestMapping(value = "authenticateTheUser", method=RequestMethod.POST)
	public String doTestLogin(@RequestParam("user_id") String userId, @RequestParam("user_password") String userPwd) {

		logger.info("*** test id : " + userId);
		logger.info("*** test pwd : " + userPwd);
		
		return "login-test.html";
	}
}

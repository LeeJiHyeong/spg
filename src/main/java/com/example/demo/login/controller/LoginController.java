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
public class LoginController {
	
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value = "login_test")
	public String goTestLogin() {
		
		return "login_test.html";
	}
	
	@RequestMapping(value = "do_login_test", method=RequestMethod.POST)
	public String doTestLogin(@RequestParam("user_id") String userId, @RequestParam("user_password") String userPwd) {

		logger.info("*** test id : " + userId);
		logger.info("*** test pwd : " + userPwd);
		
		return "login_test.html";
	}
}

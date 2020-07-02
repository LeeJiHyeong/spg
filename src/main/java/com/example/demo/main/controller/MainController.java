package com.example.demo.main.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping(value = "/")
	public String goMain(HttpSession session, Model model) {
		
		// session
		String userId = null;
		
		if (session.getAttribute("userName") != null) {
			userId = (String)session.getAttribute("userName");
		}
		
		model.addAttribute("userId", userId);
		
		return "index.html";
	}

	@RequestMapping(value = "/accessDenied")
	public String accessDenied(){
		return "access-deny.html";
	}
	
	@RequestMapping(value = "/test1")
	public String test1(){
		return "access-deny.html";
	}
	
	@RequestMapping(value = "/test2")
	public String test2(){
		return "access-deny.html";
	}
}

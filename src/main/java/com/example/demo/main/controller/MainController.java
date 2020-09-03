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
		if (session.getAttribute("userName") != null) {
			String userName = (String)session.getAttribute("userName");
			model.addAttribute("userName", userName);
		}
		
		return "index.html";
	}

	@RequestMapping(value = "/accessDenied")
	public String accessDenied(){
		return "access-deny.html";
	}
	
	@RequestMapping(value = "/ready")
	public String goReadyPage() {
		return "ready";
	}

}

package com.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping(value = "/")
	public ModelAndView goMain() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}
}

package com.example.demo.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/Main")
public class MainController {

	@RequestMapping(value = "/")
	public ModelAndView goMain() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index.html");
		return mav;
	}

	@RequestMapping(value = "/accessDenied")
	public String accessDenied(){
		return "access-deny.html";
	}
	
	@RequestMapping(value = "gallery")
	public String gallery(){
		return "/board/gallery.html";
	}
	
	@RequestMapping(value = "freeBoard")
	public String freeBoard(){
		return "/board/free_board.html";
	}
	
	@RequestMapping(value = "eduBoard")
	public String eduBoard(){
		return "/board/education_board.html";
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

package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
	
	@RequestMapping(value = "/test")
	public ModelAndView test() {
		
		Abc abc = new Abc();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("test");
		mav.addObject("sex", abc);
		
		return mav;
	}
	
	class Abc {
		String s = "sex2";
		
		public String getS() {
			return this.s;
		}
	}
	
}

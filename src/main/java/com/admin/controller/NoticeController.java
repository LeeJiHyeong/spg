package com.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoticeController {
	
	
	// 공지사항 컨트롤러
	@RequestMapping(value = "temp")
	public String goNotice() {
		
		return "";
	}
}

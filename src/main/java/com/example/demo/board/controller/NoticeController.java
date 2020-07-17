package com.example.demo.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NoticeController {
	

    // 공지사항
    @RequestMapping(value = "notice")
    public String goNotice() {

        return "admin/notice";
    }
}

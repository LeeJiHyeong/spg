package com.example.demo.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	

    // 공지사항
    @RequestMapping(value = "noticeBoard")
    public String goNotice() {

        return "admin/notice";
    }
}

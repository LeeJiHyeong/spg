package com.example.demo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {


    // 공지사항 컨트롤러
    @RequestMapping(value = "temp")
    public String goNotice() {

        return "";
    }
}

package com.example.demo.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.login.service.UserPrincipal;

@Controller
@RequestMapping("/board")
public class GalleryControlelr {

    // 갤러리
    @RequestMapping(value = "gallery")
    public String goGallery(HttpSession session, Model model) {

        UserPrincipal user = (UserPrincipal) session.getAttribute("user");
        model.addAttribute("userName", user.getUsername());

        return "board/gallery";
    }

}

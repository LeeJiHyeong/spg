package com.example.demo.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EduBoardController {
	
    // 교육게시판
    @RequestMapping(value = "eduBoard")
    public String goEducationBoard() {

        return "board/education_board";
    }
}

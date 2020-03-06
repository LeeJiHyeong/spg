package com.example.demo.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoardController {
	
	// 자유게시판 컨트롤러
	@RequestMapping(value = "board")
	public String goFreeBoard() {
		
		return "board/free_board";
	}
}

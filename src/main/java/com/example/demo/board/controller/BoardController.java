package com.example.demo.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoardController {
	
	// 자유게시판
	@RequestMapping(value = "go_board")
	public String goFreeBoard() {
		
		return "board/free_board";
	}
	
	// 교육게시판
	@RequestMapping(value = "go_edu_board")
	public String goEducationBoard() {
		
		return "board/education_board";
	}
	
	// 갤러리
	@RequestMapping(value = "go_gallery")
	public String goGallery() {
		
		return "board/gallery";
	}
	
	// 공지사항
	@RequestMapping(value = "go_notice")
	public String goNotice() {
		
		return "admin/notice";
	}
	
	// 글작성
	@RequestMapping(value = "go_write")
	public String goWrite() {
		
		return "write-page";
	}
}

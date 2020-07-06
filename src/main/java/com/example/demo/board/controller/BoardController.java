package com.example.demo.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.annotation.ModelMethodProcessor;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.board.service.FreeBoardService;
import com.example.demo.board.domain.*;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	private Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private FreeBoardService freeBoardService;
	
	// 자유게시판
	@RequestMapping(value = "freeBoard")
	public String goFreeBoard(HttpSession session, Model model) {
		
		System.out.println(">>> 자유게시판");
		
		String userId = null;
		
		if (session.getAttribute("userName") != null) {
			userId = (String)session.getAttribute("userName");
		}
		
		model.addAttribute("userId", userId);
		return "/board/free_board";
	}
	
	@RequestMapping(value = "freeBoard/detail")
	public String goFreeBoardDetail() {
		
		return "/board/free_board_detail";
	}
	
	// 글작성
	@RequestMapping(value = "freeBoard/write")
	public String goWrite(HttpSession session, Model model) {
		
		String userId = null;
		
		if (session.getAttribute("userName") != null) {
			userId = (String)session.getAttribute("userName");
		}
		
		model.addAttribute("userId", userId);
		return "/board/free_board_write";
	}
	
	// 교육게시판
	@RequestMapping(value = "eduBoard")
	public String goEducationBoard() {
		
		return "board/education_board";
	}
	
	// 갤러리
	@RequestMapping(value = "gallery")
	public String goGallery() {
		
		return "board/gallery";
	}
	
	// 공지사항
	@RequestMapping(value = "notice")
	public String goNotice() {
		
		return "admin/notice";
	}
	
	@RequestMapping(value="doWrite")
	public String doWrite() {
		
		FreeBoard testWrite = new FreeBoard("테스트 타이틀", 1, "존", "테스트 컨텐트");
		this.freeBoardService.save(testWrite);
		
		return "/";
	}
}

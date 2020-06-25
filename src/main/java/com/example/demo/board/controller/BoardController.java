package com.example.demo.board.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.annotation.ModelMethodProcessor;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {
	
	private Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	// 자유게시판
	@RequestMapping(value = "go_board")
	public ModelAndView goFreeBoard(HttpSession session) {
		
		System.out.println(">>> 자유게시판");

		String name = (String)session.getAttribute("name");
		String userName = (String)session.getAttribute("userName");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/free_board");
		mav.addObject("userName", userName);
		mav.addObject("name", name);
		
		return mav;
	}
	
	@RequestMapping(value = "go_board_detail")
	public String goFreeBoardDetail() {
		
		return "/board/free_board_detail";
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
	public ModelAndView goWrite() {
		
		String contentWriter = "서강준"; // 임시 작성자 데이터, 로그인 세션에서 데이터 따와야함
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("write-page");
		mav.addObject("content_writer", contentWriter);

		return mav;
	}
	
	@RequestMapping(value="do_write")
	public String doWrite() {
		logger.info("*** 게시글 저장 프로세스");
		
		return "#";
	}
}

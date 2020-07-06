package com.example.demo.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.annotation.ModelMethodProcessor;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.board.service.FreeBoardService;
import com.example.demo.login.domain.User;
import com.example.demo.login.service.UserService;
import com.example.demo.board.domain.*;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	private Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private FreeBoardService freeBoardService;
	
	@Autowired
	private UserService userService;
	
	// 자유게시판
	@RequestMapping(value = "freeBoard")
	public String goFreeBoard(HttpSession session, Model model) {
		
		if (session.getAttribute("userName") != null) {
			String userName = (String)session.getAttribute("userName");
			model.addAttribute("userName", userName);
		}
		
		return "/board/free_board";
	}
	
	@RequestMapping(value = "freeBoard/detail")
	public String goFreeBoardDetail() {
		
		return "/board/free_board_detail";
	}
	
	// 글작성
	@RequestMapping(value = "freeBoard/write")
	public String goWrite(HttpSession session, Model model) {
		
		if (session.getAttribute("userName") != null) {
			String userName = (String)session.getAttribute("userName");
			User user = this.userService.findByUserName(userName);
			Long writerId = user.getId();
			
			model.addAttribute("userName", userName);
			model.addAttribute("writerId", writerId);
		}
		
		return "/board/free_board_write";
	}
	
	@PostMapping(value = "freeBoard/doWrite")
	public String doWrite(@ModelAttribute @Valid FreeBoard freeBoard) {
		
		System.out.println(">>> 게시글 저장 프로세스");
		
		this.freeBoardService.save(freeBoard);
		
		return "redirect:/board/freeBoard";
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
	
}

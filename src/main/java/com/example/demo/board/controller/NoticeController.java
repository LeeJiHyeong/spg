package com.example.demo.board.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.board.domain.NoticeBoard;
import com.example.demo.board.domain.NoticeBoardComment;
import com.example.demo.board.domain.NoticeBoardFile;
import com.example.demo.board.service.NoticeBoardService;
import com.example.demo.login.domain.User;
import com.example.demo.login.service.UserService;
import com.example.demo.utils.DateFormatter;
import com.example.demo.utils.FilePath;
import com.example.demo.utils.PageVO;

@Controller
@RequestMapping("/board")
public class NoticeController {

	private Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private NoticeBoardService noticeBoardService;

	@Autowired
	private UserService userService;

	// 자유게시판
	@GetMapping(value = "noticeBoard")
	public String goNoticeBoard(HttpSession session, Model model,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "searchKey", defaultValue = "") String searchKey,
			@RequestParam(value = "searchType", defaultValue = "") String searchType) {

		// session
		if (session.getAttribute("userName") != null) {
			String userName = (String) session.getAttribute("userName");
			model.addAttribute("userName", userName);
		}

		int totalCount;
		List<NoticeBoard> pageList;
		PageVO pageInfo = new PageVO();

		if (searchKey.equals("")) { // 검색키워드가 없는 경우
			pageList = this.noticeBoardService.findByPage(pageNum - 1);
			totalCount = this.noticeBoardService.getTotalCount();
		} else {
			if (searchType.equals("search_all")) {
				pageList = this.noticeBoardService.findByTitleContainingOrContentContaining(pageNum - 1, searchKey);
				totalCount = this.noticeBoardService.getCountByTitleContainingOrContentContaining(searchKey);
			} else {
				pageList = this.noticeBoardService.findByWriterNameContaining(pageNum - 1, searchKey);
				totalCount = this.noticeBoardService.getCountByWriterNameContaining(searchKey);
			}
		}

		if (totalCount > 0) {
			pageInfo.setPageSize(10);
			pageInfo.setPageNo(pageNum);
			pageInfo.setTotalCount(totalCount);
		}

		model.addAttribute("pageList", pageList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("searchKey", searchKey);
		model.addAttribute("searchType", searchType);
		return "/board/notice-board";
	}

	// todo: please fix this part with frontend
	@GetMapping(value = "noticeBoard/detail")
	public String goNoticeBoardDetail(HttpSession session, Model model, @RequestParam("contentId") long contentId) {

		if (session.getAttribute("userName") != null) {
			String userName = (String) session.getAttribute("userName");
			model.addAttribute("userName", userName);
		}

		NoticeBoard content = this.noticeBoardService.getNoticeBoardDetail(contentId);
		List<NoticeBoardFile> noticeBoardFiles = content.getNoticeBoardFile();

		model.addAttribute("contentTitle", content.getTitle());
		model.addAttribute("writerName", content.getWriterName());
		model.addAttribute("contentText", content.getContent());
		model.addAttribute("contentId", content.getId());
		model.addAttribute("commentList", content.getNoticeBoardComment());
		model.addAttribute("commentCount", this.noticeBoardService.getCommentCountByContentId(contentId));

		if (noticeBoardFiles != null && noticeBoardFiles.size() != 0) {
			model.addAttribute("fileName", noticeBoardFiles.get(0).getOrdinaryFileName());
		}

		return "/board/notice-board-detail";
	}
//
//	// 글작성
//	@RequestMapping(value = "NoticeBoard/write")
//	public String goWrite(HttpSession session, Model model) {
//
//		if (session.getAttribute("userName") != null) {
//			String userName = (String) session.getAttribute("userName");
//			User user = this.userService.findByUserName(userName);
//			Long writerId = user.getId();
//
//			model.addAttribute("userName", userName);
//			model.addAttribute("writerId", writerId);
//		}
//
//		return "/board/Notice_board_write";
//	}
//
//	@PostMapping(value = "NoticeBoard/doWrite")
//	public String doWrite(@ModelAttribute @Valid NoticeBoard NoticeBoard,
//			@RequestParam("upload") MultipartFile uploadFile) {
//
//		System.out.println(">>> 게시글 저장 프로세스");
//
//		// TODO:: 이지형
//
//		String ordinaryFileName = uploadFile.getOriginalFilename();
//
//		if (ordinaryFileName != null && !ordinaryFileName.equals("")) {
//			String storeFileName = UUID.randomUUID().toString();
//			String fileSize = Long.toString(uploadFile.getSize());
//			String fileExt = ordinaryFileName.substring(ordinaryFileName.lastIndexOf(".") + 1);
//
//			try {
//				File file = new File(FilePath.NoticeBoard.getFilePath() + storeFileName);
//				uploadFile.transferTo(file);
//
//				Long NoticeBoardId = this.NoticeBoardService.save(NoticeBoard).getId();
//				NoticeBoardFile NoticeBoardfile = new NoticeBoardFile(storeFileName, ordinaryFileName, NoticeBoardId);
//				this.NoticeBoardService.save(NoticeBoardfile);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//			this.NoticeBoardService.save(NoticeBoard);
//		}
//
//		return "redirect:/board/NoticeBoard";
//	}
//
	@GetMapping(value = "noticeBoard/doWriteComment")
	@ResponseBody
	public Map<String, String> doWriteComment(@ModelAttribute @Valid NoticeBoardComment noticeBoardComment,
			HttpSession session) {

		String userName = (String) session.getAttribute("userName");

		// 수정코드
		Map<String, String> comment = new HashMap<String, String>();
		comment.put("content", noticeBoardComment.getContent());
		comment.put("contentId", Long.toString(noticeBoardComment.getContentId()));
		comment.put("userName", userName);

		noticeBoardComment.setUserName(userName);
		NoticeBoardComment insertedNoticeBoardComment = this.noticeBoardService.save(noticeBoardComment);

		DateFormatter dfm = new DateFormatter();

		comment.put("createDate", dfm.getDate(insertedNoticeBoardComment.getCreateDate()));
		comment.put("commentId", Long.toString(insertedNoticeBoardComment.getId()));
		comment.put("commentCount",
				Long.toString(this.noticeBoardService.getCommentCountByContentId(noticeBoardComment.getContentId())));

		return comment;
	}
//
//	@GetMapping(value = "NoticeBoard/doDelete")
//	public String doDeleteNoticeBoard(@RequestParam(value = "contentId") int contentId) {
//		this.NoticeBoardService.deleteFilesAndNoticeBoardDataByContentId(contentId);
//
//		return "redirect:/board/NoticeBoard";
//	}
//
	@GetMapping(value = "noticeBoard/doDeleteComment")
	@ResponseBody
	public Map<String, String> doDeleteNoticeBoardComment(@RequestParam(value = "commentId") Long commentId,
			@RequestParam(value = "contentId") Long contentId) {
		this.noticeBoardService.deleteComment(commentId);

		Map<String, String> deletedComment = new HashMap<String, String>();
		deletedComment.put("commentId", Long.toString(commentId));
		deletedComment.put("commentCount", Long.toString(this.noticeBoardService.getCommentCountByContentId(contentId)));
		return deletedComment;
	}
//
//	@GetMapping(value = "NoticeBoard/modify")
//	public String goNoticeBoardModify(@RequestParam(value = "contentId") int contentId, HttpSession session,
//			Model model) {
//
//		if (session.getAttribute("userName") != null) {
//			String userName = (String) session.getAttribute("userName");
//			User user = this.userService.findByUserName(userName);
//			Long writerId = user.getId();
//
//			model.addAttribute("userName", userName);
//			model.addAttribute("writerId", writerId);
//		}
//
//		NoticeBoard content = this.NoticeBoardService.getNoticeBoardDetail(contentId);
//		List<NoticeBoardFile> NoticeBoardFiles = content.getNoticeBoardFile();
//
//		if (NoticeBoardFiles != null && NoticeBoardFiles.size() != 0) {
//			// 1게시물 1파일이기때문에 get(0)
//			// 다수파일 추가하도록 변경하게되면 수정 필요
//			model.addAttribute("fileName", NoticeBoardFiles.get(0).getOrdinaryFileName());
//		}
//
//		model.addAttribute("content", content);
//		return "/board/Notice-board-modify";
//	}
//
//	@PostMapping("/NoticeBoard/doModifyNoticeBoardDetail")
//	public String doModifyData(@ModelAttribute @Valid NoticeBoard NoticeBoard,
//			@RequestParam("upload") MultipartFile uploadFile) {
//
//		// preprocessing update
//		NoticeBoard prevNoticeBoard = this.NoticeBoardService.getNoticeBoardDetail(NoticeBoard.getId());
//		NoticeBoard.setCreateDate(prevNoticeBoard.getCreateDate());
//		NoticeBoard.setNumberOfHit(prevNoticeBoard.getNumberOfHit());
//		boolean result = this.NoticeBoardService.modifyNoticeBoardDetail(NoticeBoard);
//
//		if (result) {
//			// todo :ljh -> files store in here and write next page direction
//			return "redirect:/board/NoticeBoard/detail?contentId=" + NoticeBoard.getId(); // update well
//		}
//		return ""; // error
//	}

}

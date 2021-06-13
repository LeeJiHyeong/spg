package com.cnu.spg.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.cnu.spg.domain.board.NoticeBoard;
import com.cnu.spg.domain.board.NoticeBoardComment;
import com.cnu.spg.domain.board.NoticeBoardFile;
import com.cnu.spg.board.service.NoticeBoardService;
import com.cnu.spg.utils.DateFormatter;
import com.cnu.spg.utils.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnu.spg.user.service.UserService;

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

}

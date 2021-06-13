package com.cnu.spg.board.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.cnu.spg.domain.board.EduBoardComment;
import com.cnu.spg.board.service.EduBoardService;
import com.cnu.spg.domain.login.User;
import com.cnu.spg.utils.DateFormatter;
import com.cnu.spg.utils.FilePath;
import com.cnu.spg.utils.PageVO;
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

import com.cnu.spg.domain.board.EduBoard;
import com.cnu.spg.domain.board.EduBoardFile;
import com.cnu.spg.user.service.UserService;

@Controller
@RequestMapping("/board")
public class EduBoardController {

	private Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private EduBoardService eduBoardService;

	@Autowired
	private UserService userService;

	// 자유게시판
	@GetMapping(value = "eduBoard")
	public String goEduBoard(HttpSession session, Model model,
			@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
			@RequestParam(value = "searchKey", defaultValue = "") String searchKey,
			@RequestParam(value = "searchType", defaultValue = "") String searchType) {

		// session
		if (session.getAttribute("userName") != null) {
			String userName = (String) session.getAttribute("userName");
			model.addAttribute("userName", userName);
		}

		int totalCount;
		List<EduBoard> pageList;
		PageVO pageInfo = new PageVO();

		if (searchKey.equals("")) { // 검색키워드가 없는 경우
			pageList = this.eduBoardService.findByPage(pageNum - 1);
			totalCount = this.eduBoardService.getTotalCount();
		} else {
			if (searchType.equals("search_all")) {
				pageList = this.eduBoardService.findByTitleContainingOrContentContaining(pageNum - 1, searchKey);
				totalCount = this.eduBoardService.getCountByTitleContainingOrContentContaining(searchKey);
			} else {
				pageList = this.eduBoardService.findByWriterNameContaining(pageNum - 1, searchKey);
				totalCount = this.eduBoardService.getCountByWriterNameContaining(searchKey);
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
		return "/board/edu-board";
	}

	// todo: please fix this part with frontend
	@GetMapping(value = "eduBoard/detail")
	public String goEduBoardDetail(HttpSession session, Model model, @RequestParam("contentId") long contentId) {

		if (session.getAttribute("userName") != null) {
			String userName = (String) session.getAttribute("userName");
			model.addAttribute("userName", userName);
		}

		EduBoard content = this.eduBoardService.getEduBoardDetail(contentId);
		List<EduBoardFile> eduBoardFiles = content.getEduBoardFile();

		model.addAttribute("contentTitle", content.getTitle());
		model.addAttribute("writerName", content.getWriterName());
		model.addAttribute("contentText", content.getContent());
		model.addAttribute("contentId", content.getId());
		model.addAttribute("commentList", content.getEduBoardComment());
		model.addAttribute("commentCount", this.eduBoardService.getCommentCountByContentId(contentId));

		if (eduBoardFiles != null && eduBoardFiles.size() != 0) {
			model.addAttribute("fileName", eduBoardFiles.get(0).getOrdinaryFileName());
		}

		return "/board/edu-board-detail";
	}

	// 글작성
	@RequestMapping(value = "eduBoard/write")
	public String goWrite(HttpSession session, Model model) {

		if (session.getAttribute("userName") != null) {
			String userName = (String) session.getAttribute("userName");
			User user = this.userService.findByUserName(userName);
			Long writerId = user.getId();

			model.addAttribute("userName", userName);
			model.addAttribute("writerId", writerId);
		}

		return "/board/edu-board-write";
	}

	@PostMapping(value = "eduBoard/doWrite")
	public String doWrite(@ModelAttribute @Valid EduBoard eduBoard,
			@RequestParam("upload") MultipartFile uploadFile) {

		String ordinaryFileName = uploadFile.getOriginalFilename();

		if (ordinaryFileName != null && !ordinaryFileName.equals("")) {
			String storeFileName = UUID.randomUUID().toString();
			String fileSize = Long.toString(uploadFile.getSize());
			String fileExt = ordinaryFileName.substring(ordinaryFileName.lastIndexOf(".") + 1);

			try {
				File file = new File(FilePath.EduBoard.getFilePath() + storeFileName);
				uploadFile.transferTo(file);

				Long eduBoardId = this.eduBoardService.save(eduBoard).getId();
				EduBoardFile eduBoardfile = new EduBoardFile(storeFileName, ordinaryFileName, eduBoardId);
				this.eduBoardService.save(eduBoardfile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.eduBoardService.save(eduBoard);
		}

		return "redirect:/board/eduBoard";
	}

	@GetMapping(value = "eduBoard/doWriteComment")
	@ResponseBody
	public Map<String, String> doWriteComment(@ModelAttribute @Valid EduBoardComment eduBoardComment,
			HttpSession session) {

		String userName = (String) session.getAttribute("userName");

		// 수정코드
		Map<String, String> comment = new HashMap<String, String>();
		comment.put("content", eduBoardComment.getContent());
		comment.put("contentId", Long.toString(eduBoardComment.getContentId()));
		comment.put("userName", userName);

		eduBoardComment.setUserName(userName);
		EduBoardComment insertedEduBoardComment = this.eduBoardService.save(eduBoardComment);

		DateFormatter dfm = new DateFormatter();

		comment.put("createDate", dfm.getDate(insertedEduBoardComment.getCreateDate()));
		comment.put("commentId", Long.toString(insertedEduBoardComment.getId()));
		comment.put("commentCount",
				Long.toString(this.eduBoardService.getCommentCountByContentId(eduBoardComment.getContentId())));

		return comment;
	}

	@GetMapping(value = "eduBoard/doDelete")
	public String doDeleteEduBoard(@RequestParam(value = "contentId") int contentId) {
		this.eduBoardService.deleteFilesAndEduBoardDataByContentId(contentId);

		return "redirect:/board/eduBoard";
	}

	@GetMapping(value = "eduBoard/doDeleteComment")
	@ResponseBody
	public Map<String, String> doDeleteEduBoardComment(@RequestParam(value = "commentId") Long commentId,
			@RequestParam(value = "contentId") Long contentId) {
		this.eduBoardService.deleteComment(commentId);

		Map<String, String> deletedComment = new HashMap<String, String>();
		deletedComment.put("commentId", Long.toString(commentId));
		deletedComment.put("commentCount", Long.toString(this.eduBoardService.getCommentCountByContentId(contentId)));
		return deletedComment;
	}

	@GetMapping(value = "eduBoard/modify")
	public String goEduBoardModify(@RequestParam(value = "contentId") int contentId, HttpSession session,
			Model model) {

		if (session.getAttribute("userName") != null) {
			String userName = (String) session.getAttribute("userName");
			User user = this.userService.findByUserName(userName);
			Long writerId = user.getId();

			model.addAttribute("userName", userName);
			model.addAttribute("writerId", writerId);
		}

		EduBoard content = this.eduBoardService.getEduBoardDetail(contentId);
		List<EduBoardFile> EduBoardFiles = content.getEduBoardFile();

		if (EduBoardFiles != null && EduBoardFiles.size() != 0) {
			// 1게시물 1파일이기때문에 get(0)
			// 다수파일 추가하도록 변경하게되면 수정 필요
			model.addAttribute("fileName", EduBoardFiles.get(0).getOrdinaryFileName());
		}

		model.addAttribute("content", content);
		return "/board/edu-board-modify";
	}

	@PostMapping("/eduBoard/doModifyEduBoardDetail")
	public String doModifyData(@ModelAttribute @Valid EduBoard eduBoard,
			@RequestParam("upload") MultipartFile uploadFile) {

		// preprocessing update
		EduBoard prevEduBoard = this.eduBoardService.getEduBoardDetail(eduBoard.getId());
		eduBoard.setCreateDate(prevEduBoard.getCreateDate());
		eduBoard.setNumberOfHit(prevEduBoard.getNumberOfHit());
		boolean result = this.eduBoardService.modifyEduBoardDetail(eduBoard);

		if (result) {
			// todo :ljh -> files store in here and write next page direction
			return "redirect:/board/EduBoard/detail?contentId=" + eduBoard.getId(); // update well
		}
		return ""; // error
	}
}

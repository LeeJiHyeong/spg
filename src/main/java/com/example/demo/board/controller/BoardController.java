package com.example.demo.board.controller;

import com.example.demo.board.domain.FreeBoard;
import com.example.demo.board.domain.FreeBoardFile;
import com.example.demo.board.service.FreeBoardFileService;
import com.example.demo.board.service.FreeBoardService;
import com.example.demo.login.domain.User;
import com.example.demo.login.service.UserPrincipal;
import com.example.demo.login.service.UserService;
import com.example.demo.utils.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/board")
public class BoardController {

    private Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    private FreeBoardService freeBoardService;

    @Autowired
    private FreeBoardFileService freeBoardFileService;

    @Autowired
    private UserService userService;

    // 자유게시판
    @GetMapping(value = "freeBoard")
    public String goFreeBoard(HttpSession session, Model model,
                              @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "searchKey", defaultValue = "") String searchKey) {

        // session
        if (session.getAttribute("userName") != null) {
            String userName = (String) session.getAttribute("userName");
            model.addAttribute("userName", userName);
        }

        int totalCount;
        List<FreeBoard> pageList;
        PageVO pageInfo = new PageVO();

        if (searchKey.equals("")) { // 검색키워드가 없는 경우
            pageList = this.freeBoardService.findByPage(pageNum - 1);
            totalCount = this.freeBoardService.getTotalCount();
        } else {
            pageList = this.freeBoardService.findByTitleContainingOrContentContaining(pageNum - 1, searchKey);
            totalCount = this.freeBoardService.getCountByTitleContainingOrContentContaining(searchKey);
        }

        if (totalCount > 0) {
            pageInfo.setPageSize(10);
            pageInfo.setPageNo(pageNum);
            pageInfo.setTotalCount(totalCount);
        }

        model.addAttribute("pageList", pageList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("searchKey", searchKey);

        return "/board/free_board";
    }

    // todo: please fix this part with frontend
    @GetMapping(value = "freeBoard/detail")
    public String goFreeBoardDetail(HttpSession session, Model model,
                                    @RequestParam("contentId") long contentId) {

        if (session.getAttribute("userName") != null) {
            String userName = (String) session.getAttribute("userName");
            model.addAttribute("userName", userName);
        }

        FreeBoard content = this.freeBoardService.getFreeBoardDetail(contentId);
        List<FreeBoardFile> freeBoardFiles = content.getFreeBoardFile();

        model.addAttribute("contentTitle", content.getTitle());
        model.addAttribute("writerName", content.getWriterName());
        model.addAttribute("contentText", content.getContent());

        if (freeBoardFiles != null && freeBoardFiles.size() != 0) {
            model.addAttribute("fileName", freeBoardFiles.get(0).getOrdinaryFileName());
        }

        return "/board/free_board_detail";
    }

    // 글작성
    @RequestMapping(value = "freeBoard/write")
    public String goWrite(HttpSession session, Model model) {

        if (session.getAttribute("userName") != null) {
            String userName = (String) session.getAttribute("userName");
            User user = this.userService.findByUserName(userName);
            Long writerId = user.getId();

            model.addAttribute("userName", userName);
            model.addAttribute("writerId", writerId);
        }

        return "/board/free_board_write";
    }

    @PostMapping(value = "freeBoard/doWrite")
    public String doWrite(@ModelAttribute @Valid FreeBoard freeBoard
            , @RequestParam("upload") MultipartFile uploadFile) {

        System.out.println(">>> 게시글 저장 프로세스");

        //TODO:: 이지형

        String ordinaryFileName = uploadFile.getOriginalFilename();

        if (!ordinaryFileName.equals("")) {
            String storeFileName = UUID.randomUUID().toString();
            String fileSize = Long.toString(uploadFile.getSize());
            String fileExt = ordinaryFileName.substring(ordinaryFileName.lastIndexOf(".") + 1);

            try {
                File file = new File("C:/spg_file/" + storeFileName);
                uploadFile.transferTo(file);

                Long freeBoardId = this.freeBoardService.save(freeBoard).getId();
                FreeBoardFile freeBoardfile = new FreeBoardFile(storeFileName, ordinaryFileName, freeBoardId);
                this.freeBoardFileService.save(freeBoardfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.freeBoardService.save(freeBoard);
        }

        return "redirect:/board/freeBoard";
    }

}

package com.cnu.spg.admin.controller;

import com.cnu.spg.admin.reponse.ReponseUserData;
import com.cnu.spg.admin.request.RequestModifyUserRole;
import com.cnu.spg.admin.service.AdminService;
import com.cnu.spg.domain.board.NoticeBoard;
import com.cnu.spg.domain.board.NoticeBoardFile;
import com.cnu.spg.domain.board.service.NoticeBoardService;
import com.cnu.spg.domain.login.Role;
import com.cnu.spg.domain.login.RoleName;
import com.cnu.spg.domain.login.User;
import com.cnu.spg.domain.login.UserPrincipal;
import com.cnu.spg.domain.login.service.UserService;
import com.cnu.spg.utils.FilePath;
import com.cnu.spg.utils.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private NoticeBoardService noticeBoardService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "adminNotice")
    public String goNotice(HttpSession session, Model model) {
        UserPrincipal user = (UserPrincipal) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("userName", user.getUsername());

        return "admin/admin-notice";
    }

    @RequestMapping(value = "adminDeleteUser")
    public String goDeleteUser(HttpSession session, Model model) {
        UserPrincipal user = (UserPrincipal) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("userName", user.getUsername());

        return "admin/admin-delete-user";
    }

    @PostMapping("/doModifyUserRole")
    public String doModifyUserUserRole(@Valid @ModelAttribute("requestModifyUserRole") RequestModifyUserRole requestModifyUserRole) {

        Set<Role> roles = new HashSet<>();

        try {
            for (String roleStr : requestModifyUserRole.getRoles()) {
                roles.add(new Role(RoleName.valueOf(roleStr)));
            }
        } catch (Exception e) {
            return "redirect:/admin/goModifyUserDataPage"; // fail
        }

        if (this.adminService.changeUserAuthenticated(Long.parseLong(requestModifyUserRole.getId()), requestModifyUserRole.getUserName(), roles)) {
            return "redirect:/admin/goModifyUserDataPage"; // change well
        }

        return "redirect:/admin/goModifyUserDataPage"; // change fail

    }

    @PostMapping("/doDeleteUser")
    public String doDeleteUser(@Valid @ModelAttribute("requestModifyUserRole") RequestModifyUserRole requestModifyUserRole) {

        Long userId = Long.parseLong(requestModifyUserRole.getId());
        String username = requestModifyUserRole.getUserName();

        if (this.adminService.deleteUserData(userId, username)) {
            return "redirect:/admin/goModifyUserDataPage"; // delete success
        }

        return "redirect:admin/goModifyUserDataPage"; // fail to delete
    }

    @GetMapping("/goModifyUserDataPage")
    public String goModifyUserDataPage(HttpSession session, Model model,
                                       @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {

        UserPrincipal user = (UserPrincipal) session.getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        PageVO pageInfo = new PageVO();
        List<ReponseUserData> userList = this.adminService.findUsersByPage(pageNumber - 1);
        long totalCount = this.adminService.getTotalCount();

        List<Role> temp = Arrays.stream(RoleName.values()).map(Role::new).collect(Collectors.toList());

        if (totalCount > 0) {
            pageInfo.setPageSize(10);
            pageInfo.setPageNo(pageNumber);
            pageInfo.setTotalCount((int) totalCount);
        }

        model.addAttribute("roles", temp);
        model.addAttribute("userName", user.getUsername());
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("users", userList);

        return "admin/admin-management";
    }

    // 공지글 작성
    @RequestMapping(value = "noticeBoard/write")
    public String goWrite(HttpSession session, Model model) {

        if (session.getAttribute("userName") != null) {
            String userName = (String) session.getAttribute("userName");
            User user = this.userService.findByUserName(userName);
            Long writerId = user.getId();

            model.addAttribute("userName", userName);
            model.addAttribute("writerId", writerId);
        }

        return "/board/notice-board-write";
    }

    // 공지글 등록 프로세스
    @PostMapping(value = "noticeBoard/doWrite")
    public String doWrite(@ModelAttribute @Valid NoticeBoard noticeBoard,
                          @RequestParam("upload") MultipartFile uploadFile) {

        // TODO:: 이지형

        String ordinaryFileName = uploadFile.getOriginalFilename();

        if (ordinaryFileName != null && !ordinaryFileName.equals("")) {
            String storeFileName = UUID.randomUUID().toString();
            String fileSize = Long.toString(uploadFile.getSize());
            String fileExt = ordinaryFileName.substring(ordinaryFileName.lastIndexOf(".") + 1);

            try {
                File file = new File(FilePath.NoticeBoard.getFilePath() + storeFileName);
                uploadFile.transferTo(file);

                Long NoticeBoardId = this.noticeBoardService.save(noticeBoard).getId();
                NoticeBoardFile NoticeBoardfile = new NoticeBoardFile(storeFileName, ordinaryFileName, NoticeBoardId);
                this.noticeBoardService.save(NoticeBoardfile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            this.noticeBoardService.save(noticeBoard);
        }

        return "redirect:/board/noticeBoard/detail?contentId=" + noticeBoard.getId();
    }

    @GetMapping(value = "noticeBoard/doDelete")
    public String doDeleteNoticeBoard(@RequestParam(value = "contentId") int contentId) {
        this.noticeBoardService.deleteFilesAndNoticeBoardDataByContentId(contentId);

        return "redirect:/board/noticeBoard";
    }

    @GetMapping(value = "noticeBoard/modify")
    public String goNoticeBoardModify(@RequestParam(value = "contentId") int contentId, HttpSession session,
                                      Model model) {

        if (session.getAttribute("userName") != null) {
            String userName = (String) session.getAttribute("userName");
            User user = this.userService.findByUserName(userName);
            Long writerId = user.getId();

            model.addAttribute("userName", userName);
            model.addAttribute("writerId", writerId);
        }

        NoticeBoard content = this.noticeBoardService.getNoticeBoardDetail(contentId);
        List<NoticeBoardFile> noticeBoardFiles = content.getNoticeBoardFile();

        if (noticeBoardFiles != null && noticeBoardFiles.size() != 0) {
            // 1게시물 1파일이기때문에 get(0)
            // 다수파일 추가하도록 변경하게되면 수정 필요
            model.addAttribute("fileName", noticeBoardFiles.get(0).getOrdinaryFileName());
        }

        model.addAttribute("content", content);
        return "/board/notice-board-modify";
    }

    @PostMapping("/noticeBoard/doModifyNoticeBoardDetail")
    public String doModifyData(@ModelAttribute @Valid NoticeBoard noticeBoard,
                               @RequestParam("upload") MultipartFile uploadFile) {

        // preprocessing update
        NoticeBoard prevNoticeBoard = this.noticeBoardService.getNoticeBoardDetail(noticeBoard.getId());
        noticeBoard.setCreateDate(prevNoticeBoard.getCreateDate());
        noticeBoard.setNumberOfHit(prevNoticeBoard.getNumberOfHit());
        boolean result = this.noticeBoardService.modifyNoticeBoardDetail(noticeBoard);

        if (result) {
            // todo :ljh -> files store in here and write next page direction
            return "redirect:/board/noticeBoard/detail?contentId=" + noticeBoard.getId(); // update well
        }
        return ""; // error
    }
}

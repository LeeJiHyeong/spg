package com.example.demo.admin.controller;

import com.example.demo.admin.request.RequestModifyUserRole;
import com.example.demo.admin.service.AdminService;
import com.example.demo.login.domain.RoleName;
import com.example.demo.login.domain.User;
import com.example.demo.utils.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/doModifyUserRole")
    public String doModifyUserUserRole(@Valid @ModelAttribute("user") RequestModifyUserRole requestModifyUserRole) {
        String roleStr = "ROLE_" + requestModifyUserRole.getRoleStr();
        RoleName roleName;
        try {
            roleName = RoleName.valueOf(roleStr);
        } catch (Exception e) {
            return ""; // fail
        }

        if (this.adminService.changeUserAuthenticated(requestModifyUserRole.getId(), requestModifyUserRole.getUsername(), roleName)) {
            return ""; // change well
        }

        return ""; // change fail
    }

    @PostMapping("/doDeleteUser")
    public String doDeleteUser(@RequestParam("userId") Long userId,
                               @RequestParam("username") String username) {
        if (this.adminService.deleteUserData(userId, username)) {
            return ""; // delete success
        }

        return ""; // fail to delete
    }

    @GetMapping("/goModifyUserDataPage")
    public String goModifyUserDataPage(Model model,
                                       @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
        PageVO pageInfo = new PageVO();
        List<User> users = this.adminService.findUsersByPage(pageNumber);
        long totalCount = this.adminService.getTotalCount();
        if (totalCount > 0) {
            pageInfo.setPageSize(10);
            pageInfo.setPageNo(pageNumber);
            pageInfo.setTotalCount((int) totalCount);
        }
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("users", users);

        return "";
    }
}

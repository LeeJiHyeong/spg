package com.example.demo.admin.controller;

import com.example.demo.admin.reponse.ReponseUserData;
import com.example.demo.admin.request.RequestModifyUserRole;
import com.example.demo.admin.service.AdminService;
import com.example.demo.login.domain.Role;
import com.example.demo.login.domain.RoleName;
import com.example.demo.login.service.UserPrincipal;
import com.example.demo.utils.PageVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    
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
        	System.out.println("check");      	
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
}

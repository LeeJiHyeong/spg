package com.example.demo.admin.controller;

import com.example.demo.admin.reponse.ReponseUserData;
import com.example.demo.admin.request.RequestModifyUserRole;
import com.example.demo.admin.service.AdminService;
import com.example.demo.login.controller.LoginController;
import com.example.demo.login.domain.Role;
import com.example.demo.login.domain.RoleName;
import com.example.demo.login.service.UserPrincipal;
import com.example.demo.utils.PageVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    private Logger logger = LoggerFactory.getLogger(LoginController.class);    
    
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

//    @PostMapping("/doModifyUserRole")
//    public String doModifyUserUserRole(@RequestBody RequestModifyUserRole requestModifyUserRole) {
//    	    	
//    	System.out.println(requestModifyUserRole.getUsername());
//    	System.out.println(requestModifyUserRole.getId());
//    	
//    	Iterator<String> iter = requestModifyUserRole.getRoles().iterator();
//    	while(iter.hasNext()) {
//    		System.out.println(iter.next());
//    	}
//    	
//        return ""; // change fail
//        
//    }
    @PostMapping("/doModifyUserRole")
    public String doModifyUserUserRole(@Valid @ModelAttribute("requestModifyUserRole") RequestModifyUserRole requestModifyUserRole) {
    	
    	System.out.println(requestModifyUserRole.getId());
    	System.out.println(requestModifyUserRole.getUserName());
    	Iterator<String> iter = requestModifyUserRole.getRoles().iterator();
    	
    	while(iter.hasNext()) {
    		System.out.println(iter.next());
    	}
    	
//    	String roleHeader = "ROLE_";
//        Set<Role> roles = new HashSet<>();
//        try {
//            for (String roleStr : requestModifyUserRole.getRoles()) {
//                roles.add(new Role(RoleName.valueOf(roleHeader + roleStr)));
//            }
//        } catch (Exception e) {
//            return ""; // fail
//        }
//
//        if (this.adminService.changeUserAuthenticated(requestModifyUserRole.getId(), requestModifyUserRole.getUsername(), roles)) {
//            return ""; // change well
//        }

        return "redirect:/"; // change fail
        
    }

    @PostMapping("/doDeleteUser")
    public String doDeleteUser(@Valid @ModelAttribute("requestModifyUserRole") RequestModifyUserRole requestModifyUserRole) {
    	
    	System.out.println("check : "+requestModifyUserRole.getId());
    	System.out.println("check2 : "+requestModifyUserRole.getUserName());
    	
//    	Long userId = Long.parseLong(requestModifyUserRole.getId());
//    	String username = requestModifyUserRole.getUserName();
//    	
//        if (this.adminService.deleteUserData(userId, username)) {
//            return "admin/admin-delete-user"; // delete success
//        }

        return "redirect:/"; // fail to delete
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

package com.zerone.scms.controller;

import com.zerone.scms.model.Academic;
import com.zerone.scms.model.Role;
import com.zerone.scms.model.User;
import com.zerone.scms.service.AcademicService;
import com.zerone.scms.service.RoleService;
import com.zerone.scms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTMLDocument;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private AcademicService academicService;

    @RequestMapping("/test1")
    public Role test1(String roleName) {
        Role role = roleService.queryRoleByRoleName(roleName);
        return role;
    }

    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    public String test2(Academic academic) {
        academicService.saveAcademic(academic);
        return "OK";
    }

    @RequestMapping("/test3")
    public List<User> test3() {
        List<User> userList = userService.getAllUsers();
        return userList;
    }

//    @RequestMapping("/test1")
//    public User hello(ExtendedModelMap model, HttpSession httpSession){
//        User user = userService.queryUserByUsername("admin");
//        return user;
//    }
//    @RequestMapping("/test2")
//    @PreAuthorize("hasAuthority('reset_user_psw')")
//    public String good(ExtendedModelMap model, HttpSession httpSession){
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if ("anonymousUser".equals(principal)){
//            System.out.println("anonymous");
//        }else {
//            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;
//            System.out.println(user.getUsername());
//            Collection<GrantedAuthority> authorities = user.getAuthorities();
//            Iterator<GrantedAuthority> iterator = authorities.iterator();
//            while (iterator.hasNext()){
//                GrantedAuthority authority = iterator.next();
//                System.out.println(authority.getAuthority());
//            }
//        }
//        return "good";
//    }
}

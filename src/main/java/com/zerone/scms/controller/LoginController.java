package com.zerone.scms.controller;

import com.zerone.scms.model.*;
import com.zerone.scms.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private AcademicService academicService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ClassService classService;

    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeachingTaskService teachingTaskService;

    @RequestMapping("/login_page")
    public String login(ExtendedModelMap model, HttpSession httpSession) {
        return "login_page";
    }

    @RequestMapping("/login_success")
    public String login_success(ExtendedModelMap model, HttpSession httpSession) {
        String role = "";
        String page = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ("anonymousUser".equals(principal)) {
            System.out.println("anonymous");
        } else {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) principal;
            System.out.println(user.getUsername());
            Collection<GrantedAuthority> authorities = user.getAuthorities();
            Iterator<GrantedAuthority> iterator = authorities.iterator();
            while (iterator.hasNext()) {
                GrantedAuthority authority = iterator.next();
                String authorityName = authority.getAuthority();
                if (authorityName.startsWith("ROLE_")) {
                    role = authorityName;
                    System.out.println(authority.getAuthority());
                    break;
                }
            }
        }
        if (role.equals("ROLE_admin")) {
            try {
                List<User> userList = userService.getAllUsers();
                List<Academic> academics = academicService.getAllAcademics();
                model.addAttribute("userList", userList);
                model.addAttribute("academics", academics);
                model.addAttribute("Loginer", "admin");
                model.addAttribute("pageType", "user");
                page = "admin_page";
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
                page = "error";
            }

        } else if (role.equals("ROLE_academic")) {
            try {
                List<Teacher> teachers = teacherService.getAllTeachers();
                model.addAttribute("teachers", teachers);

                List<ClassAndGrade> classAndGrades = classService.getAllClasses();
                model.addAttribute("classAndGrades", classAndGrades);

                List<Student> students = studentService.getAllStudents();
                model.addAttribute("students", students);

                List<Course> courses = courseService.getAllCourses();
                model.addAttribute("courses", courses);

                List<TeachingTask> teachingTasks = teachingTaskService.getAllTeachingTasks();
                model.addAttribute("teachingTasks", teachingTasks);

                model.addAttribute("Loginer", ((org.springframework.security.core.userdetails.User) principal).getUsername());
                model.addAttribute("pageType", "teacher");
                page = "academic_page";
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
                page = "error";
            }

        } else if (role.equals("ROLE_teacher")) {
            page = "teacher_page";

        } else if (role.equals("ROLE_student")) {
            page = "stu_page";
        }
        return page;
    }
}

package com.zerone.scms.controller;

import com.zerone.scms.model.Academic;
import com.zerone.scms.model.ClassAndGrade;
import com.zerone.scms.model.Teacher;
import com.zerone.scms.model.User;
import com.zerone.scms.service.ClassService;
import com.zerone.scms.service.TeacherService;
import com.zerone.scms.service.UserService;
import com.zerone.scms.util.ResponseConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    Logger logger = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    TeacherService teacherService;

    @Autowired
    UserService userService;
    @Autowired
    ClassService classService;

    @PostAuthorize("hasAuthority('teacher_write')")//Post和Pre的区别post如果该请求没有相应授权，不会返回相应视图，但是代码会正常执行
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveTeacher(ExtendedModelMap model, Teacher teacher) {
        String result = ResponseConstant.OK;
        try {
            if (teacherService.validateTeacher(teacher)) {
                if (teacher.getId() == null) {
                    if (teacherService.selectTeacherByJobNo(teacher.getJobNo()) != null) {
                        result = ResponseConstant.DUPLICATE_ENTRY;
                    }
                } else {
                    Teacher oldTeacher = teacherService.selectTeacherByJobNo(teacher.getJobNo());
                    if (oldTeacher != null && !oldTeacher.getId().equals(teacher.getId())) {
                        System.out.println("DUPLICATE_ENTRY2");
                        result = ResponseConstant.DUPLICATE_ENTRY;
                    }
                }
            } else {
                result = ResponseConstant.VALIDATION_FAILED;
            }
            if (result.equals(ResponseConstant.OK)) {
                teacherService.saveTeacher(teacher);
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = ResponseConstant.RUN_ERROR;
        }
        return result;
    }

    @PostAuthorize("hasAuthority('teacher_read')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String getTeacherPage(String menu, String Loginer, Model model) {
        String result = "academic_page";
        try {
            List<ClassAndGrade> classAndGrades = classService.getAllClasses();
            model.addAttribute("classAndGrades", classAndGrades);

            List<Teacher> teachers = teacherService.getAllTeachers();
            model.addAttribute("teachers", teachers);
            model.addAttribute("Loginer", Loginer);
            model.addAttribute("pageType", menu);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = "error";
        }
        return result;
    }


    //    @PostAuthorize("hasAuthority('teacher_read')")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String queryAcademicByRealName(String realname, String Loginer, Model model) {
        String result = "academic_page";
        try {
            List<Teacher> teachers = null;
            if (realname == null || realname.length() == 0) {
                teachers = teacherService.getAllTeachers();
            } else {
                teachers = teacherService.selectTeachersByRealName(realname);
            }

            List<ClassAndGrade> classAndGrades = classService.getAllClasses();
            model.addAttribute("classAndGrades", classAndGrades);


            model.addAttribute("teachers", teachers);
            model.addAttribute("Loginer", Loginer);
            model.addAttribute("pageType", "teacher");
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = "error";
        }
        return result;
    }

    @PostAuthorize("hasAuthority('teacher_write')")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public String removeTeacher(Long id) {
        String result = ResponseConstant.OK;
        try {
            if (id != null) {
                if (teacherService.getTeacherById(id) != null) {
                    teacherService.deleteTeacher(id);
                } else {
                    result = ResponseConstant.VALIDATION_FAILED;
                }
            } else {
                result = ResponseConstant.VALIDATION_FAILED;
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = ResponseConstant.RUN_ERROR;
        }
        return result;
    }
}

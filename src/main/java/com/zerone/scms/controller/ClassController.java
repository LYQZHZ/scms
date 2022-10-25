package com.zerone.scms.controller;


import com.zerone.scms.model.ClassAndGrade;
import com.zerone.scms.model.Teacher;
import com.zerone.scms.service.ClassService;
import com.zerone.scms.service.TeacherService;
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
@RequestMapping("/class")
public class ClassController {
    Logger logger = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    ClassService classService;
    @Autowired
    TeacherService teacherService;

    @PostAuthorize("hasAuthority('class_grade_write')")//Post和Pre的区别post如果该请求没有相应授权，不会返回相应视图，但是代码会正常执行
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveClass(ExtendedModelMap model, ClassAndGrade classAndGrade) {
        String result = ResponseConstant.OK;
        try {
            if (classService.validateClass(classAndGrade)) {
                if (classAndGrade.getId() == null) {
                    if (classService.selectClassByClassNameOne(classAndGrade.getClassName()) != null) {
                        result = ResponseConstant.DUPLICATE_ENTRY;
                    }
                } else {
                    ClassAndGrade oldClassAndGrade = classService.selectClassByClassNameOne(classAndGrade.getClassName());
                    if (oldClassAndGrade != null && !oldClassAndGrade.getId().equals(classAndGrade.getId())) {
                        System.out.println("DUPLICATE_ENTRY2");
                        result = ResponseConstant.DUPLICATE_ENTRY;
                    }
                }
            } else {
                result = ResponseConstant.VALIDATION_FAILED;
            }
            if (result.equals(ResponseConstant.OK)) {
                classService.saveClass(classAndGrade);
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = ResponseConstant.RUN_ERROR;
        }
        return result;
    }

    @PostAuthorize("hasAuthority('class_grade_read')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String getClassPage(String menu, String Loginer, Model model) {
        String result = "academic_page";
        try {
            List<Teacher> teachers = teacherService.getAllTeachers();
            model.addAttribute("teachers", teachers);
            List<ClassAndGrade> classAndGrades = classService.getAllClasses();
            model.addAttribute("classAndGrades", classAndGrades);
            model.addAttribute("Loginer", Loginer);
            model.addAttribute("pageType", menu);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = "error";
        }
        return result;
    }


    @PostAuthorize("hasAuthority('class_grade_read')")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String queryClassByClassName(String className, String Loginer, Model model) {
        String result = "academic_page";
        try {
            List<ClassAndGrade> classAndGrades = null;
            if (className == null || className.length() == 0) {
                classAndGrades = classService.getAllClasses();
            } else {
                classAndGrades = classService.selectClassesByClassName(className);
            }
            List<Teacher> teachers = teacherService.getAllTeachers();
            model.addAttribute("teachers", teachers);
            model.addAttribute("classAndGrades", classAndGrades);
            model.addAttribute("Loginer", Loginer);
            model.addAttribute("pageType", "class");
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = "error";
        }
        return result;
    }

    @PostAuthorize("hasAuthority('class_grade_write')")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public String removeClass(Long id) {
        String result = ResponseConstant.OK;
        try {
            if (id != null) {
                if (classService.getClassById(id) != null) {
                    classService.deleteClass(id);
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

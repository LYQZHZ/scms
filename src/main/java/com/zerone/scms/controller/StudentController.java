package com.zerone.scms.controller;

import com.zerone.scms.model.ClassAndGrade;
import com.zerone.scms.model.Student;
import com.zerone.scms.model.Teacher;
import com.zerone.scms.service.ClassService;
import com.zerone.scms.service.StudentService;
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
@RequestMapping("/student")
public class StudentController {

    Logger logger = LoggerFactory.getLogger(TeacherController.class);
    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    UserService userService;
    @Autowired
    ClassService classService;

    @PostAuthorize("hasAuthority('student_write')")//Post和Pre的区别post如果该请求没有相应授权，不会返回相应视图，但是代码会正常执行
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(ExtendedModelMap model, Student student, String major) {
        String result = ResponseConstant.OK;
        ClassAndGrade classAndGrade = classService.selectClassByClassNameOne(major);
        student.setClassGradesId(classAndGrade.getId());
        try {
            if (studentService.validateStudent(student)) {
                if (student.getId() == null) {
                    if (studentService.selectStudentBySno(student.getSno()) != null) {
                        result = ResponseConstant.DUPLICATE_ENTRY;
                    }
                } else {
                    Student oldStudent = studentService.selectStudentBySno(student.getSno());
                    if (oldStudent != null && !oldStudent.getId().equals(student.getId())) {
                        System.out.println("DUPLICATE_ENTRY2");
                        result = ResponseConstant.DUPLICATE_ENTRY;
                    }
                }
            } else {
                result = ResponseConstant.VALIDATION_FAILED;
            }
            if (result.equals(ResponseConstant.OK)) {
                studentService.saveStudent(student);
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = ResponseConstant.RUN_ERROR;
        }
        return result;
    }

    @PostAuthorize("hasAuthority('student_read')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String getStudentPage(String menu, String Loginer, Model model) {
        String result = "academic_page";
        try {
            List<ClassAndGrade> classAndGrades = classService.getAllClasses();
            model.addAttribute("classAndGrades", classAndGrades);

            List<Teacher> teachers = teacherService.getAllTeachers();
            model.addAttribute("teachers", teachers);

            List<Student> students = studentService.getAllStudents();
            model.addAttribute("students", students);

            model.addAttribute("Loginer", Loginer);
            model.addAttribute("pageType", menu);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = "error";
        }
        return result;
    }


    @PostAuthorize("hasAuthority('student_read')")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String queryStudentByRealName(String realname, String Loginer, Model model) {
        String result = "academic_page";
        try {
            List<Student> students = null;
            if (realname == null || realname.length() == 0) {
                students = studentService.getAllStudents();
            } else {
                students = studentService.selectStudentsByRealName(realname);
            }

            List<ClassAndGrade> classAndGrades = classService.getAllClasses();
            model.addAttribute("classAndGrades", classAndGrades);

            List<Teacher> teachers = teacherService.getAllTeachers();
            model.addAttribute("teachers", teachers);
            model.addAttribute("students", students);
            model.addAttribute("Loginer", Loginer);
            model.addAttribute("pageType", "student");
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = "error";
        }
        return result;
    }

    @PostAuthorize("hasAuthority('student_write')")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public String removeStudent(Long id) {
        String result = ResponseConstant.OK;
        try {
            if (id != null) {
                if (studentService.getStudentById(id) != null) {
                    studentService.deleteStudent(id);
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

package com.zerone.scms.controller;

import com.zerone.scms.model.ClassAndGrade;
import com.zerone.scms.model.Course;
import com.zerone.scms.model.Student;
import com.zerone.scms.model.Teacher;
import com.zerone.scms.service.ClassService;
import com.zerone.scms.service.CourseService;
import com.zerone.scms.service.StudentService;
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
@RequestMapping("/course")
public class CourseController {
    Logger logger = LoggerFactory.getLogger(CourseController.class);
    @Autowired
    StudentService studentService;
    @Autowired
    ClassService classService;
    @Autowired
    CourseService courseService;
    @Autowired
    TeacherService teacherService;

    @PostAuthorize("hasAuthority('course_write')")//Post和Pre的区别post如果该请求没有相应授权，不会返回相应视图，但是代码会正常执行
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveCourse(ExtendedModelMap model, Course course) {
        String result = ResponseConstant.OK;
        try {
            if (courseService.validateCourse(course)) {
                if (course.getId() == null) {
                    if (courseService.selectCourseByCno(course.getCno()) != null) {
                        result = ResponseConstant.DUPLICATE_ENTRY;
                    }
                } else {
                    Course oldCourse = courseService.selectCourseByCno(course.getCno());
                    if (oldCourse != null && !oldCourse.getId().equals(course.getId())) {
                        System.out.println("DUPLICATE_ENTRY2");
                        result = ResponseConstant.DUPLICATE_ENTRY;
                    }
                }
            } else {
                result = ResponseConstant.VALIDATION_FAILED;
            }
            if (result.equals(ResponseConstant.OK)) {
                courseService.saveCourse(course);
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = ResponseConstant.RUN_ERROR;
        }
        return result;
    }

    @PostAuthorize("hasAuthority('course_read')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String getCoursePage(String menu, String Loginer, Model model) {
        String result = "academic_page";
        try {
            List<ClassAndGrade> classAndGrades = classService.getAllClasses();
            model.addAttribute("classAndGrades", classAndGrades);

            List<Teacher> teachers = teacherService.getAllTeachers();
            model.addAttribute("teachers", teachers);

            List<Student> students = studentService.getAllStudents();
            model.addAttribute("students", students);

            List<Course> courses = courseService.getAllCourses();
            model.addAttribute("courses", courses);

            model.addAttribute("Loginer", Loginer);
            model.addAttribute("pageType", menu);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = "error";
        }
        return result;
    }


    @PostAuthorize("hasAuthority('course_read')")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String queryCourseByCourseName(String courseName, String Loginer, Model model) {
        String result = "academic_page";
        try {
            List<Course> courses = null;
            if (courseName == null || courseName.length() == 0) {
                courses = courseService.getAllCourses();
            } else {
                courses = courseService.selectCoursesByCourseName(courseName);
            }
            List<ClassAndGrade> classAndGrades = classService.getAllClasses();
            model.addAttribute("classAndGrades", classAndGrades);

            List<Teacher> teachers = teacherService.getAllTeachers();
            model.addAttribute("teachers", teachers);

            List<Student> students = studentService.getAllStudents();
            model.addAttribute("students", students);

            model.addAttribute("courses", courses);
            model.addAttribute("Loginer", Loginer);
            model.addAttribute("pageType", "course");
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = "error";
        }
        return result;
    }

    @PostAuthorize("hasAuthority('course_write')")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public String removeCourse(Long id) {
        String result = ResponseConstant.OK;
        try {
            if (id != null) {
                if (courseService.getCourseById(id) != null) {
                    courseService.deleteCourse(id);
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

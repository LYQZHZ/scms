package com.zerone.scms.controller;

import com.zerone.scms.model.*;
import com.zerone.scms.service.*;
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
@RequestMapping("teachingTask")
public class TeachingTaskController {
    Logger logger = LoggerFactory.getLogger(TeachingTaskController.class);
    @Autowired
    StudentService studentService;
    @Autowired
    ClassService classService;
    @Autowired
    CourseService courseService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    TeachingTaskService teachingTaskService;

    @PostAuthorize("hasAuthority('teaching_task_write')")//Post和Pre的区别post如果该请求没有相应授权，不会返回相应视图，但是代码会正常执行
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveTeachingTask(ExtendedModelMap model, TeachingTask teachingTask) {
        String result = ResponseConstant.OK;
        try {
            if (teachingTaskService.validateTeachingTask(teachingTask)) {
                if (teachingTask.getId() == null) {
                    TeachingTask OldTeachingTask = teachingTaskService.queryTeachingTaskByTtName(teachingTask.getTtname());
                    if (OldTeachingTask != null) {
                        if (OldTeachingTask.getClassAndGrade().getId().equals(teachingTask.getClassAndGrade().getId()) && OldTeachingTask.getCourse().getId().equals(teachingTask.getCourse().getId()) && OldTeachingTask.getTeacher().getId().equals(teachingTask.getTeacher().getId())) {
                            result = ResponseConstant.DUPLICATE_ENTRY;
                        }
                    }
                } else {
                    TeachingTask OldTeachingTask = teachingTaskService.queryTeachingTaskByTtName(teachingTask.getTtname());
                    if (OldTeachingTask != null && !OldTeachingTask.getId().equals(teachingTask.getId())) {
                        System.out.println("DUPLICATE_ENTRY2");
                        result = ResponseConstant.DUPLICATE_ENTRY;
                    }
                }
            } else {
                result = ResponseConstant.VALIDATION_FAILED;
            }
            if (result.equals(ResponseConstant.OK)) {
                teachingTaskService.saveTeachingTask(teachingTask);
            }
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = ResponseConstant.RUN_ERROR;
        }
        return result;
    }

    @PostAuthorize("hasAuthority('teaching_task_read')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public String getTeachingTaskPage(String menu, String Loginer, Model model) {
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

            List<TeachingTask> teachingTasks = teachingTaskService.getAllTeachingTasks();
            model.addAttribute("teachingTasks", teachingTasks);

            model.addAttribute("Loginer", Loginer);
            model.addAttribute("pageType", menu);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = "error";
        }
        return result;
    }


    @PostAuthorize("hasAuthority('teaching_task_read')")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public String queryTeachingTaskByTtName(String ttname, String Loginer, Model model) {
        String result = "academic_page";
        try {
            List<TeachingTask> teachingTasks = null;
            if (ttname == null || ttname.length() == 0) {
                teachingTasks = teachingTaskService.getAllTeachingTasks();
            } else {
                teachingTasks = teachingTaskService.queryTeachingTasksByTtName(ttname);
            }
            List<ClassAndGrade> classAndGrades = classService.getAllClasses();
            model.addAttribute("classAndGrades", classAndGrades);

            List<Teacher> teachers = teacherService.getAllTeachers();
            model.addAttribute("teachers", teachers);

            List<Student> students = studentService.getAllStudents();
            model.addAttribute("students", students);

            List<Course> courses = courseService.getAllCourses();
            model.addAttribute("courses", courses);

            model.addAttribute("teachingTasks", teachingTasks);
            model.addAttribute("Loginer", Loginer);
            model.addAttribute("pageType", "teachingTask");
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            result = "error";
        }
        return result;
    }

    @PostAuthorize("hasAuthority('teaching_task_write')")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public String removeTeachingTask(Long id) {
        String result = ResponseConstant.OK;
        try {
            if (id != null) {
                if (teachingTaskService.getTeachingTaskById(id) != null) {
                    teachingTaskService.deleteTeachingTask(id);
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

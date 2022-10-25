package com.zerone.scms.service;

import com.zerone.scms.mapper.ClassMapper;
import com.zerone.scms.mapper.CourseMapper;
import com.zerone.scms.model.ClassAndGrade;
import com.zerone.scms.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("courseService")
public class CourseServiceImp implements CourseService {
    Logger logger = LoggerFactory.getLogger(CourseServiceImp.class);
    @Autowired
    CourseMapper courseMapper;

    @Override
    public boolean saveCourse(Course course) {
        Boolean result = true;
        Long id = course.getId();
        if (null == id) {
            if (1 != courseMapper.insertCourse(course)) {
                result = false;
                throw new RuntimeException("课程信息保存失败！");
            }
        } else {
            if (1 != courseMapper.updateCourse(course)) {
                result = false;
                throw new RuntimeException("课程信息更新失败！");
            }
        }
        return result;
    }


    @Override
    public Course getCourseById(Long id) {
        return courseMapper.selectCourseById(id);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseMapper.selectAllCourses();
    }

    @Override
    public List<Course> selectCoursesByCourseName(String className) {
        return courseMapper.selectCoursesByCourseName(className);
    }

    @Override
    public Course selectCourseByCno(String cno) {
        return courseMapper.selectCourseByCno(cno);
    }

    @Override
    public boolean deleteCourse(Long id) {
        Boolean result = true;
        Course course = courseMapper.selectCourseById(id);
        if (course != null) {
            if (1 != courseMapper.deleteCourse(course.getId())) {
                result = false;
                throw new RuntimeException("删除课程信息失败！");
            }
        } else {
            result = false;
            throw new RuntimeException("要删除的课程不存在！");
        }
        return result;
    }

    @Override
    public boolean validateCourse(Course course) {
        boolean result = true;

        if (course.getCno() == null || course.getCno().length() != 10) {
            result = false;
        }
        if (course.getCname() == null || course.getCname().length() == 0) {
            result = false;
        }
        if (course.getPeriod() == null) {
            result = false;
        }
        if (course.getCredit() == null) {
            result = false;
        }

        return result;
    }
}

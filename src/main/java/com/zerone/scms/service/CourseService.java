package com.zerone.scms.service;


import com.zerone.scms.model.Course;

import java.util.List;

public interface CourseService {

    boolean saveCourse(Course course);

    Course getCourseById(Long id);

    List<Course> getAllCourses();

    List<Course> selectCoursesByCourseName(String className);

    Course selectCourseByCno(String cno);

    boolean deleteCourse(Long id);

    boolean validateCourse(Course course);
}

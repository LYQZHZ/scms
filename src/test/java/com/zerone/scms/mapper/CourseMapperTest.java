package com.zerone.scms.mapper;

import com.zerone.scms.model.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseMapperTest {
    @Autowired
    CourseMapper courseMapper;

    @Test
    @Transactional
    @Rollback
    void insertCourse() {
        Course course = new Course();
        course.setCno("test0001");
        course.setCname("计算机导论");
        course.setPeriod(16);
        course.setCredit(0.5);
        assertEquals(1, courseMapper.insertCourse(course));
        assertNotNull(course.getId());
        Course newCourse = courseMapper.selectCourseById(course.getId());
        assertEquals(newCourse, course);
    }

    @Test
    @Transactional
    @Rollback
    void updateCourse() {
        Course course = new Course();
        course.setCno("10000001");
        course.setCname("计算机导论");
        course.setPeriod(16);
        course.setCredit(0.5);
        courseMapper.insertCourse(course);
        Course newCourse = courseMapper.selectCourseById(course.getId());
        newCourse.setCno("10000002");
        newCourse.setCname("计算机组成原理");
        newCourse.setPeriod(64);
        newCourse.setCredit(2.0);
        assertEquals(1, courseMapper.updateCourse(newCourse));
        Course lastCourse = courseMapper.selectCourseById(newCourse.getId());
        assertEquals(newCourse, lastCourse);
    }

    @Test
    @Transactional
    @Rollback
    void deleteCourse() {
        Course course = new Course();
        course.setCno("10000001");
        course.setCname("计算机导论");
        course.setPeriod(16);
        course.setCredit(0.5);
        courseMapper.insertCourse(course);
        assertEquals(1, courseMapper.deleteCourse(course.getId()));
        Course newCourse = courseMapper.selectCourseById(course.getId());
        assertNull(newCourse);
    }

    @Test
    @Transactional
    @Rollback
    void selectCourseById() {
        Course course = new Course();
        course.setCno("10000001");
        course.setCname("计算机导论");
        course.setPeriod(16);
        course.setCredit(0.5);
        assertEquals(1, courseMapper.insertCourse(course));
        assertNotNull(course.getId());
        Course newCourse = courseMapper.selectCourseById(course.getId());
        assertEquals(newCourse, course);
    }

    @Test
    @Transactional
    @Rollback
    void selectCoursesByName() {
        List<Course> oldCourses1 = courseMapper.selectCoursesByCourseName("计算机");
        List<Course> oldCourses2 = courseMapper.selectCoursesByCourseName("高级");
        List<Course> oldCourses3 = courseMapper.selectCoursesByCourseName("基础");
        List<Course> courses = new ArrayList<Course>();
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCno("test000" + i);
            course.setPeriod(64);
            if (i < 5) {
                course.setCname("计算机基础" + i);
                course.setCredit(2.0);
            } else {
                course.setCname("计算机高级" + (i - 5));
                course.setCredit(2.5);
            }
            courseMapper.insertCourse(course);
            courses.add(course);
        }
        assertEquals(10, courses.size());
        List<Course> allCourses1 = courseMapper.selectCoursesByCourseName("计算机");
        List<Course> allCourses2 = courseMapper.selectCoursesByCourseName("高级");
        List<Course> allCourses3 = courseMapper.selectCoursesByCourseName("基础");
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        for (Course course : courses) {
            for (int i = oldCourses1.size(); i < allCourses1.size(); i++) {
                if (course.getId().longValue() == allCourses1.get(i).getId().longValue()) {
                    assertEquals(course, allCourses1.get(i));
                    count1++;
                }
            }
            for (int i = oldCourses2.size(); i < allCourses2.size(); i++) {
                if (course.getId().longValue() == allCourses2.get(i).getId().longValue()) {
                    assertEquals(course, allCourses2.get(i));
                    count2++;
                }
            }
            for (int i = oldCourses3.size(); i < allCourses3.size(); i++) {
                if (course.getId().longValue() == allCourses3.get(i).getId().longValue()) {
                    assertEquals(course, allCourses3.get(i));
                    count3++;
                }
            }
        }
        assertEquals(10, count1);
        assertEquals(5, count2);
        assertEquals(5, count3);
    }

    @Test
    @Transactional
    @Rollback
    void selectAllCourses() {
        List<Course> oldCourses = courseMapper.selectAllCourses();
        List<Course> courses = new ArrayList<Course>();
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCno("test000" + i);
            course.setPeriod(64);
            if (i < 6) {
                course.setCname("计算机基础" + i);
                course.setCredit(2.0);
            } else {
                course.setCname("计算机高级" + (i - 5));
                course.setCredit(2.5);
            }
            courseMapper.insertCourse(course);
            courses.add(course);
        }
        assertEquals(10, courses.size());
        List<Course> allCourses = courseMapper.selectAllCourses();
        int count = 0;
        for (Course course : courses) {
            for (int i = oldCourses.size(); i < allCourses.size(); i++) {
                if (course.getId().longValue() == allCourses.get(i).getId().longValue()) {
                    assertEquals(course, allCourses.get(i));
                    count++;
                }
            }
        }
        assertEquals(10, count);
    }
}
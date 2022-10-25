package com.zerone.scms.service;

import com.zerone.scms.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeachingTaskServiceImpTest {
    @Autowired
    TeachingTaskService teachingTaskService;
    @Autowired
    ClassService classService;
    @Autowired
    CourseService courseService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentService studentService;

    @Test
    @Transactional
    @Rollback
    void queryTeachingTaskByTtName() throws ParseException {
        List<TeachingTask> oldTeachingTasks1 = teachingTaskService.queryTeachingTasksByTtName("课程教学任务");
        List<TeachingTask> oldTeachingTasks2 = teachingTaskService.queryTeachingTasksByTtName("第1班");
        List<TeachingTask> oldTeachingTasks3 = teachingTaskService.queryTeachingTasksByTtName("计算机课程_2");
        List<ClassAndGrade> classGrades = new ArrayList<ClassAndGrade>();
        for (int i = 0; i < 5; i++) {
            ClassAndGrade classGrade = new ClassAndGrade();
            if (i % 2 == 0) {
                classGrade.setClassNo("2021");
                classGrade.setClassName("第" + i / 2 + "班");
                classGrade.setGrade("2022");
                classGrade.setMajor("计算机科学与技术");
            } else {
                classGrade.setClassNo("2022");
                classGrade.setClassName("第" + (i + 1) / 2 + "班");
                classGrade.setGrade("2021");
                classGrade.setMajor("网络工程");
            }
            classService.saveClass(classGrade);
            assertNotNull(classGrade.getId());
            classGrades.add(classGrade);
        }
        List<Course> courses = new ArrayList<Course>();
        List<Teacher> teachers = new ArrayList<Teacher>();
        for (int i = 0; i < 6; i++) {
            Course course = new Course();
            course.setCno("test000" + i);
            course.setCname("计算机课程_" + i);
            if (i % 2 == 0) {
                course.setPeriod(32);
                course.setCredit(1.0);
            } else {
                course.setPeriod(64);
                course.setCredit(2.0);
            }

            assertTrue(courseService.saveCourse(course));
            courses.add(course);

            Teacher teacher = new Teacher();
            teacher.setJobNo("ttest00" + i);
            teacher.setRealName("教师" + i);
            if (i % 2 == 0) {
                teacher.setGender("男");
                teacher.setAge(30);
                teacher.setMajor("计算机科学与技术");
                teacher.setContactNumber("11111111111");
            } else {
                teacher.setGender("女");
                teacher.setAge(28);
                teacher.setMajor("网络工程");
                teacher.setContactNumber("11111111112");
            }

            teacherService.saveTeacher(teacher);
            teachers.add(teacher);
        }

        List<TeachingTask> teachingTasks = new ArrayList<TeachingTask>();
        int eCount = 0;
        for (int i = 0; i < classGrades.size(); i++) {
            for (int j = 0; j < courses.size(); j++) {
                TeachingTask teachingTask = new TeachingTask();
                teachingTask.setClassAndGrade(classGrades.get(i));
                teachingTask.setCourse(courses.get(j));
                teachingTask.setTeacher(teachers.get(j));
                teachingTask.setTtname(classGrades.get(i).getClassName() + "的" + courses.get(j).getCname() + "课程教学任务(" + teachers.get(j).getRealName() + ")");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (j % 2 == 0) {
                    Date beginDate = sdf.parse("2022-09-01");
                    Date endDate = sdf.parse("2022-12-30");
                    teachingTask.setBeginDate(beginDate);
                    teachingTask.setEndDate(endDate);
                } else {
                    Date beginDate = sdf.parse("2023-03-01");
                    Date endDate = sdf.parse("2023-06-30");
                    teachingTask.setBeginDate(beginDate);
                    teachingTask.setEndDate(endDate);
                }
                assertTrue(teachingTaskService.saveTeachingTask(teachingTask));
                assertNotNull(teachingTask.getId());
                teachingTasks.add(teachingTask);
                eCount++;
            }
        }
        assertEquals(30, eCount);
        List<TeachingTask> allTeachingTasks1 = teachingTaskService.queryTeachingTasksByTtName("课程教学任务");
        List<TeachingTask> allTeachingTasks2 = teachingTaskService.queryTeachingTasksByTtName("第1班");
        List<TeachingTask> allTeachingTasks3 = teachingTaskService.queryTeachingTasksByTtName("计算机课程_2");
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        for (TeachingTask teachingTask : teachingTasks) {
            for (int i = oldTeachingTasks1.size(); i < allTeachingTasks1.size(); i++) {
                if (teachingTask.getId().longValue() == allTeachingTasks1.get(i).getId().longValue()) {
                    assertEquals(teachingTask, allTeachingTasks1.get(i));
                    count1++;
                }
            }
            for (int i = oldTeachingTasks2.size(); i < allTeachingTasks2.size(); i++) {
                if (teachingTask.getId().longValue() == allTeachingTasks2.get(i).getId().longValue()) {
                    assertEquals(teachingTask, allTeachingTasks2.get(i));
                    count2++;
                }
            }
            for (int i = oldTeachingTasks3.size(); i < allTeachingTasks3.size(); i++) {
                if (teachingTask.getId().longValue() == allTeachingTasks3.get(i).getId().longValue()) {
                    assertEquals(teachingTask, allTeachingTasks3.get(i));
                    count3++;
                }
            }
        }
        assertEquals(eCount, count1);
        assertEquals(12, count2);
        assertEquals(5, count3);
    }

    @Test
    @Transactional
    @Rollback
    void getAllTeachingTasks() throws ParseException {
        List<TeachingTask> oldTeachingTasks = teachingTaskService.getAllTeachingTasks();
        List<ClassAndGrade> classGrades = new ArrayList<ClassAndGrade>();
        for (int i = 0; i < 5; i++) {
            ClassAndGrade classGrade = new ClassAndGrade();
            if (i % 2 == 0) {
                classGrade.setClassNo("2021");
                classGrade.setClassName("第" + i / 2 + "班");
                classGrade.setGrade("2022");
                classGrade.setMajor("计算机科学与技术");
            } else {
                classGrade.setClassNo("2022");
                classGrade.setClassName("第" + (i + 1) / 2 + "班");
                classGrade.setGrade("2021");
                classGrade.setMajor("网络工程");
            }
            classService.saveClass(classGrade);
            assertNotNull(classGrade.getId());
        }
        List<Course> courses = new ArrayList<Course>();
        List<Teacher> teachers = new ArrayList<Teacher>();
        for (int i = 0; i < 6; i++) {
            Course course = new Course();
            course.setCno("test000" + i);
            course.setCname("计算机课程_" + i);
            if (i % 2 == 0) {
                course.setPeriod(32);
                course.setCredit(1.0);
            } else {
                course.setPeriod(64);
                course.setCredit(2.0);
            }

            assertTrue(courseService.saveCourse(course));
            courses.add(course);

            Teacher teacher = new Teacher();
            teacher.setJobNo("ttest00" + i);
            teacher.setRealName("教师" + i);
            if (i % 2 == 0) {
                teacher.setGender("男");
                teacher.setAge(30);
                teacher.setMajor("计算机科学与技术");
                teacher.setContactNumber("11111111111");
            } else {
                teacher.setGender("女");
                teacher.setAge(28);
                teacher.setMajor("网络工程");
                teacher.setContactNumber("11111111112");
            }

            teacherService.saveTeacher(teacher);
            teachers.add(teacher);
        }

        List<TeachingTask> teachingTasks = new ArrayList<TeachingTask>();
        int eCount = 0;
        for (int i = 0; i < classGrades.size(); i++) {
            for (int j = 0; j < courses.size(); j++) {
                TeachingTask teachingTask = new TeachingTask();
                teachingTask.setClassAndGrade(classGrades.get(i));
                teachingTask.setCourse(courses.get(j));
                teachingTask.setTeacher(teachers.get(j));
                teachingTask.setTtname(classGrades.get(i).getClassName() + "的" + courses.get(j).getCname() + "(课程" + teachers.get(j).getRealName() + ")");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (j % 2 == 0) {
                    Date beginDate = sdf.parse("2022-09-01");
                    Date endDate = sdf.parse("2022-12-30");
                    teachingTask.setBeginDate(beginDate);
                    teachingTask.setEndDate(endDate);
                } else {
                    Date beginDate = sdf.parse("2023-03-01");
                    Date endDate = sdf.parse("2023-06-30");
                    teachingTask.setBeginDate(beginDate);
                    teachingTask.setEndDate(endDate);
                }
                assertEquals(1, teachingTaskService.saveTeachingTask(teachingTask));
                assertNotNull(teachingTask.getId());
                teachingTasks.add(teachingTask);
                eCount++;
            }
        }
        List<TeachingTask> allTeachingTasks = teachingTaskService.getAllTeachingTasks();
        int count = 0;
        for (TeachingTask teachingTask : teachingTasks) {
            for (int i = oldTeachingTasks.size(); i < allTeachingTasks.size(); i++) {
                if (teachingTask.getId().longValue() == allTeachingTasks.get(i).getId().longValue()) {
                    assertEquals(teachingTask, allTeachingTasks.get(i));
                    count++;
                }
            }
        }
        assertEquals(eCount, count);
    }

    @Test
    @Transactional
    @Rollback
    void queryTeachingTasksByTtName() throws ParseException {
        List<TeachingTask> oldTeachingTasks1 = teachingTaskService.queryTeachingTasksByTtName("课程教学任务");
        List<TeachingTask> oldTeachingTasks2 = teachingTaskService.queryTeachingTasksByTtName("第1班");
        List<TeachingTask> oldTeachingTasks3 = teachingTaskService.queryTeachingTasksByTtName("计算机课程_2");
        List<ClassAndGrade> classGrades = new ArrayList<ClassAndGrade>();
        for (int i = 0; i < 5; i++) {
            ClassAndGrade classGrade = new ClassAndGrade();
            if (i % 2 == 0) {
                classGrade.setClassNo("2021");
                classGrade.setClassName("第" + i / 2 + "班");
                classGrade.setGrade("2022");
                classGrade.setMajor("计算机科学与技术");
            } else {
                classGrade.setClassNo("2022");
                classGrade.setClassName("第" + (i + 1) / 2 + "班");
                classGrade.setGrade("2021");
                classGrade.setMajor("网络工程");
            }
            classService.saveClass(classGrade);
            assertNotNull(classGrade.getId());
            classGrades.add(classGrade);
        }
        List<Course> courses = new ArrayList<Course>();
        List<Teacher> teachers = new ArrayList<Teacher>();
        for (int i = 0; i < 6; i++) {
            Course course = new Course();
            course.setCno("test000" + i);
            course.setCname("计算机课程_" + i);
            if (i % 2 == 0) {
                course.setPeriod(32);
                course.setCredit(1.0);
            } else {
                course.setPeriod(64);
                course.setCredit(2.0);
            }

            assertTrue(courseService.saveCourse(course));
            courses.add(course);

            Teacher teacher = new Teacher();
            teacher.setJobNo("ttest00" + i);
            teacher.setRealName("教师" + i);
            if (i % 2 == 0) {
                teacher.setGender("男");
                teacher.setAge(30);
                teacher.setMajor("计算机科学与技术");
                teacher.setContactNumber("11111111111");
            } else {
                teacher.setGender("女");
                teacher.setAge(28);
                teacher.setMajor("网络工程");
                teacher.setContactNumber("11111111112");
            }

            teacherService.saveTeacher(teacher);
            teachers.add(teacher);
        }

        List<TeachingTask> teachingTasks = new ArrayList<TeachingTask>();
        int eCount = 0;
        for (int i = 0; i < classGrades.size(); i++) {
            for (int j = 0; j < courses.size(); j++) {
                TeachingTask teachingTask = new TeachingTask();
                teachingTask.setClassAndGrade(classGrades.get(i));
                teachingTask.setCourse(courses.get(j));
                teachingTask.setTeacher(teachers.get(j));
                teachingTask.setTtname(classGrades.get(i).getClassName() + "的" + courses.get(j).getCname() + "课程教学任务(" + teachers.get(j).getRealName() + ")");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (j % 2 == 0) {
                    Date beginDate = sdf.parse("2022-09-01");
                    Date endDate = sdf.parse("2022-12-30");
                    teachingTask.setBeginDate(beginDate);
                    teachingTask.setEndDate(endDate);
                } else {
                    Date beginDate = sdf.parse("2023-03-01");
                    Date endDate = sdf.parse("2023-06-30");
                    teachingTask.setBeginDate(beginDate);
                    teachingTask.setEndDate(endDate);
                }
                assertTrue(teachingTaskService.saveTeachingTask(teachingTask));
                assertNotNull(teachingTask.getId());
                teachingTasks.add(teachingTask);
                eCount++;
            }
        }
        assertEquals(30, eCount);
        List<TeachingTask> allTeachingTasks1 = teachingTaskService.queryTeachingTasksByTtName("课程教学任务");
        List<TeachingTask> allTeachingTasks2 = teachingTaskService.queryTeachingTasksByTtName("第1班");
        List<TeachingTask> allTeachingTasks3 = teachingTaskService.queryTeachingTasksByTtName("计算机课程_2");
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        for (TeachingTask teachingTask : teachingTasks) {
            for (int i = oldTeachingTasks1.size(); i < allTeachingTasks1.size(); i++) {
                if (teachingTask.getId().longValue() == allTeachingTasks1.get(i).getId().longValue()) {
                    assertEquals(teachingTask, allTeachingTasks1.get(i));
                    count1++;
                }
            }
            for (int i = oldTeachingTasks2.size(); i < allTeachingTasks2.size(); i++) {
                if (teachingTask.getId().longValue() == allTeachingTasks2.get(i).getId().longValue()) {
                    assertEquals(teachingTask, allTeachingTasks2.get(i));
                    count2++;
                }
            }
            for (int i = oldTeachingTasks3.size(); i < allTeachingTasks3.size(); i++) {
                if (teachingTask.getId().longValue() == allTeachingTasks3.get(i).getId().longValue()) {
                    assertEquals(teachingTask, allTeachingTasks3.get(i));
                    count3++;
                }
            }
        }
        assertEquals(eCount, count1);
        assertEquals(12, count2);
        assertEquals(5, count3);
    }

    @Test
    @Transactional
    @Rollback
    void saveTeachingTask() throws ParseException {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassNo("2021");
        classGrade.setClassName("一班");
        classGrade.setGrade("2022");
        classGrade.setMajor("计算机科学与技术");
        classService.saveClass(classGrade);
        assertNotNull(classGrade.getId());

        Course course = new Course();
        course.setCno("test0001");
        course.setCname("计算机导论");
        course.setPeriod(16);
        course.setCredit(0.5);
        assertTrue(courseService.saveCourse(course));
        assertNotNull(course.getId());

        Teacher teacher = new Teacher();
        teacher.setJobNo("ttest001");
        teacher.setRealName("教师张");
        teacher.setGender("男");
        teacher.setAge(30);
        teacher.setMajor("计算机科学与技术");
        teacher.setContactNumber("11111111111");


        teacherService.saveTeacher(teacher);
        assertNotNull(teacher.getId());

        TeachingTask teachingTask = new TeachingTask();
        teachingTask.setTtname("20计科2班计算机组成课");
        teachingTask.setClassAndGrade(classGrade);
        teachingTask.setCourse(course);
        teachingTask.setTeacher(teacher);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = sdf.parse("2022-09-01");
        Date endDate = sdf.parse("2022-12-30");
        teachingTask.setBeginDate(beginDate);
        teachingTask.setEndDate(endDate);

        Student stu = new Student();
        stu.setSno("stest001");
        stu.setRealName("张同学");
        stu.setGender("男");
        stu.setAge(20);
        stu.setContactNumber("11111111111");
        stu.setClassAndGrade(classGrade);
        studentService.saveStudent(stu);

        assertTrue(teachingTaskService.saveTeachingTask(teachingTask));
        assertNotNull(teachingTask.getId());
        TeachingTask newTeachingTask = teachingTaskService.getTeachingTaskById(teachingTask.getId());
        assertEquals(teachingTask, newTeachingTask);
    }

    @Test
    @Transactional
    @Rollback
    void getTeachingTaskById() throws ParseException {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassNo("2021");
        classGrade.setClassName("一班");
        classGrade.setGrade("2022");
        classGrade.setMajor("计算机科学与技术");
        classService.saveClass(classGrade);
        assertNotNull(classGrade.getId());

        Course course = new Course();
        course.setCno("test0001");
        course.setCname("计算机导论");
        course.setPeriod(16);
        course.setCredit(0.5);
        assertTrue(courseService.saveCourse(course));
        assertNotNull(course.getId());

        Teacher teacher = new Teacher();
        teacher.setJobNo("ttest001");
        teacher.setRealName("教师张");
        teacher.setGender("男");
        teacher.setAge(30);
        teacher.setMajor("计算机科学与技术");
        teacher.setContactNumber("11111111111");

        teacherService.saveTeacher(teacher);
        assertNotNull(teacher.getId());

        TeachingTask teachingTask = new TeachingTask();
        teachingTask.setTtname("20计科2班计算机组成课");
        teachingTask.setClassAndGrade(classGrade);
        teachingTask.setCourse(course);
        teachingTask.setTeacher(teacher);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = sdf.parse("2022-09-01");
        Date endDate = sdf.parse("2022-12-30");
        teachingTask.setBeginDate(beginDate);
        teachingTask.setEndDate(endDate);
        assertTrue(teachingTaskService.saveTeachingTask(teachingTask));
        assertNotNull(teachingTask.getId());
        TeachingTask newTeachingTask = teachingTaskService.getTeachingTaskById(teachingTask.getId());
        assertEquals(teachingTask, newTeachingTask);
    }

    @Test
    @Transactional
    @Rollback
    void deleteTeachingTask() throws ParseException {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassNo("2021");
        classGrade.setClassName("一班");
        classGrade.setGrade("2022");
        classGrade.setMajor("计算机科学与技术");
        classService.saveClass(classGrade);
        assertNotNull(classGrade.getId());

        Course course = new Course();
        course.setCno("test0001");
        course.setCname("计算机导论");
        course.setPeriod(16);
        course.setCredit(0.5);
        assertTrue(courseService.saveCourse(course));
        assertNotNull(course.getId());

        Teacher teacher = new Teacher();
        teacher.setJobNo("ttest001");
        teacher.setRealName("教师张");
        teacher.setGender("男");
        teacher.setAge(30);
        teacher.setMajor("计算机科学与技术");
        teacher.setContactNumber("11111111111");

        teacherService.saveTeacher(teacher);
        assertNotNull(teacher.getId());

        TeachingTask teachingTask = new TeachingTask();
        teachingTask.setTtname("20计科2班计算机组成课");
        teachingTask.setClassAndGrade(classGrade);
        teachingTask.setCourse(course);
        teachingTask.setTeacher(teacher);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = sdf.parse("2022-09-01");
        Date endDate = sdf.parse("2022-12-30");
        teachingTask.setBeginDate(beginDate);
        teachingTask.setEndDate(endDate);
        assertTrue(teachingTaskService.saveTeachingTask(teachingTask));
        assertNotNull(teachingTask.getId());
        TeachingTask newTeachingTask = teachingTaskService.getTeachingTaskById(teachingTask.getId());
        assertNotNull(newTeachingTask);
        assertTrue(teachingTaskService.deleteTeachingTask(newTeachingTask.getId()));
        TeachingTask lastTeachingTask = teachingTaskService.getTeachingTaskById(teachingTask.getId());
        assertNull(lastTeachingTask);
    }
}
package com.zerone.scms.mapper;

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
class TeachingTaskMapperTest {
    @Autowired
    private TeachingTaskMapper teachingTaskMapper;
    @Autowired
    private ClassMapper classGradeMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;


    @Test
    @Transactional
    @Rollback
    void insertTeachingTask() throws ParseException {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassNo("2021");
        classGrade.setClassName("一班");
        classGrade.setGrade("2022");
        classGrade.setMajor("计算机科学与技术");
        classGradeMapper.insertClass(classGrade);
        assertNotNull(classGrade.getId());

        Course course = new Course();
        course.setCno("test0001");
        course.setCname("计算机导论");
        course.setPeriod(16);
        course.setCredit(0.5);
        assertEquals(1, courseMapper.insertCourse(course));
        assertNotNull(course.getId());

        Teacher teacher = new Teacher();
        teacher.setJobNo("ttest001");
        teacher.setRealName("教师张");
        teacher.setGender("男");
        teacher.setAge(30);
        teacher.setMajor("计算机科学与技术");
        teacher.setContactNumber("11111111111");

        teacher.setUsername(teacher.getJobNo());
        teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
        teacher.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("teacher");
        assertNotNull(role);
        teacher.setRole(role);
        userMapper.insertUser(teacher);
        teacherMapper.insertTeacher(teacher);
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
        assertEquals(1, teachingTaskMapper.insertTeachingTask(teachingTask));
        assertNotNull(teachingTask.getId());
        TeachingTask newTeachingTask = teachingTaskMapper.selectTeachingTaskById(teachingTask.getId());
        assertEquals(teachingTask, newTeachingTask);
    }

    @Test
    @Transactional
    @Rollback
    void updateTeachingTask() throws ParseException {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassNo("2021");
        classGrade.setClassName("一班");
        classGrade.setGrade("2022");
        classGrade.setMajor("计算机科学与技术");
        classGradeMapper.insertClass(classGrade);
        assertNotNull(classGrade.getId());

        Course course = new Course();
        course.setCno("test0001");
        course.setCname("计算机导论");
        course.setPeriod(16);
        course.setCredit(0.5);
        assertEquals(1, courseMapper.insertCourse(course));
        assertNotNull(course.getId());

        Teacher teacher = new Teacher();
        teacher.setJobNo("ttest001");
        teacher.setRealName("教师张");
        teacher.setGender("男");
        teacher.setAge(30);
        teacher.setMajor("计算机科学与技术");
        teacher.setContactNumber("11111111111");

        teacher.setUsername(teacher.getJobNo());
        teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
        teacher.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("teacher");
        assertNotNull(role);
        teacher.setRole(role);
        userMapper.insertUser(teacher);
        teacherMapper.insertTeacher(teacher);

        teacherMapper.insertTeacher(teacher);
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
        assertEquals(1, teachingTaskMapper.insertTeachingTask(teachingTask));
        assertNotNull(teachingTask.getId());
        TeachingTask newTeachingTask = teachingTaskMapper.selectTeachingTaskById(teachingTask.getId());
        assertEquals(teachingTask, newTeachingTask);

        ClassAndGrade otherClassGrade = new ClassAndGrade();
        otherClassGrade.setClassNo("2022");
        otherClassGrade.setClassName("二班");
        otherClassGrade.setGrade("2021");
        otherClassGrade.setMajor("网络工程");
        classGradeMapper.insertClass(otherClassGrade);
        assertNotNull(otherClassGrade.getId());

        Course otherCourse = new Course();
        otherCourse.setCno("test0002");
        otherCourse.setCname("计算机引论");
        otherCourse.setPeriod(24);
        otherCourse.setCredit(0.8);
        assertEquals(1, courseMapper.insertCourse(otherCourse));
        assertNotNull(otherCourse.getId());

        Teacher otherTeacher = new Teacher();
        otherTeacher.setJobNo("ttest002");
        otherTeacher.setRealName("教师李");
        otherTeacher.setGender("女");
        otherTeacher.setAge(40);
        otherTeacher.setMajor("网络工程");
        otherTeacher.setContactNumber("11111111112");

        otherTeacher.setUsername(otherTeacher.getJobNo());
        otherTeacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
        otherTeacher.setEnabled(true);
        otherTeacher.setRole(role);
        userMapper.insertUser(otherTeacher);

        teacherMapper.insertTeacher(otherTeacher);
        assertNotNull(otherTeacher.getId());
        newTeachingTask.setClassAndGrade(otherClassGrade);
        newTeachingTask.setCourse(otherCourse);
        newTeachingTask.setTeacher(otherTeacher);
        Date otherBeginDate = sdf.parse("2022-03-01");
        Date otherEndDate = sdf.parse("2022-07-30");
        newTeachingTask.setBeginDate(otherBeginDate);
        newTeachingTask.setEndDate(otherEndDate);
        teachingTaskMapper.updateTeachingTask(newTeachingTask);
        assertEquals(newTeachingTask, teachingTaskMapper.selectTeachingTaskById(newTeachingTask.getId()));
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
        classGradeMapper.insertClass(classGrade);
        assertNotNull(classGrade.getId());

        Course course = new Course();
        course.setCno("test0001");
        course.setCname("计算机导论");
        course.setPeriod(16);
        course.setCredit(0.5);
        assertEquals(1, courseMapper.insertCourse(course));
        assertNotNull(course.getId());

        Teacher teacher = new Teacher();
        teacher.setJobNo("ttest001");
        teacher.setRealName("教师张");
        teacher.setGender("男");
        teacher.setAge(30);
        teacher.setMajor("计算机科学与技术");
        teacher.setContactNumber("11111111111");

        teacher.setUsername(teacher.getJobNo());
        teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
        teacher.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("teacher");
        assertNotNull(role);
        teacher.setRole(role);
        userMapper.insertUser(teacher);
        teacherMapper.insertTeacher(teacher);
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
        assertEquals(1, teachingTaskMapper.insertTeachingTask(teachingTask));
        assertNotNull(teachingTask.getId());
        TeachingTask newTeachingTask = teachingTaskMapper.selectTeachingTaskById(teachingTask.getId());
        assertNotNull(newTeachingTask);
        assertEquals(1, teachingTaskMapper.deleteTeachingTask(newTeachingTask.getId()));
        TeachingTask lastTeachingTask = teachingTaskMapper.selectTeachingTaskById(teachingTask.getId());
        assertNull(lastTeachingTask);
    }

    @Test
    @Transactional
    @Rollback
    void selectAllTeachingTasks() throws ParseException {
        List<TeachingTask> oldTeachingTasks = teachingTaskMapper.selectAllTeachingTasks();
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
            classGradeMapper.insertClass(classGrade);
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

            assertEquals(1, courseMapper.insertCourse(course));
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
            teacher.setUsername(teacher.getJobNo());
            teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
            teacher.setEnabled(true);
            Role role = roleMapper.getRoleByRoleName("teacher");
            assertNotNull(role);
            teacher.setRole(role);
            userMapper.insertUser(teacher);
            teacherMapper.insertTeacher(teacher);
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
                assertEquals(1, teachingTaskMapper.insertTeachingTask(teachingTask));
                assertNotNull(teachingTask.getId());
                teachingTasks.add(teachingTask);
                eCount++;
            }
        }
        List<TeachingTask> allTeachingTasks = teachingTaskMapper.selectAllTeachingTasks();
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
    void selectTeachingTaskById() throws ParseException {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassNo("2021");
        classGrade.setClassName("一班");
        classGrade.setGrade("2022");
        classGrade.setMajor("计算机科学与技术");
        classGradeMapper.insertClass(classGrade);
        assertNotNull(classGrade.getId());

        Course course = new Course();
        course.setCno("test0001");
        course.setCname("计算机导论");
        course.setPeriod(16);
        course.setCredit(0.5);
        assertEquals(1, courseMapper.insertCourse(course));
        assertNotNull(course.getId());

        Teacher teacher = new Teacher();
        teacher.setJobNo("ttest001");
        teacher.setRealName("教师张");
        teacher.setGender("男");
        teacher.setAge(30);
        teacher.setMajor("计算机科学与技术");
        teacher.setContactNumber("11111111111");

        teacher.setUsername(teacher.getJobNo());
        teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
        teacher.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("teacher");
        assertNotNull(role);
        teacher.setRole(role);
        userMapper.insertUser(teacher);
        teacherMapper.insertTeacher(teacher);
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
        assertEquals(1, teachingTaskMapper.insertTeachingTask(teachingTask));
        assertNotNull(teachingTask.getId());
        TeachingTask newTeachingTask = teachingTaskMapper.selectTeachingTaskById(teachingTask.getId());
        assertEquals(teachingTask, newTeachingTask);
    }

    @Test
    @Transactional
    @Rollback
    void selectTeachingTasksByName() throws ParseException {
        List<TeachingTask> oldTeachingTasks1 = teachingTaskMapper.selectTeachingTasksByName("课程教学任务");
        List<TeachingTask> oldTeachingTasks2 = teachingTaskMapper.selectTeachingTasksByName("第1班");
        List<TeachingTask> oldTeachingTasks3 = teachingTaskMapper.selectTeachingTasksByName("计算机课程_2");
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
            classGradeMapper.insertClass(classGrade);
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

            assertEquals(1, courseMapper.insertCourse(course));
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
            teacher.setUsername(teacher.getJobNo());
            teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
            teacher.setEnabled(true);
            Role role = roleMapper.getRoleByRoleName("teacher");
            assertNotNull(role);
            teacher.setRole(role);
            userMapper.insertUser(teacher);
            teacherMapper.insertTeacher(teacher);
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
                assertEquals(1, teachingTaskMapper.insertTeachingTask(teachingTask));
                assertNotNull(teachingTask.getId());
                teachingTasks.add(teachingTask);
                eCount++;
            }
        }
        assertEquals(30, eCount);
        List<TeachingTask> allTeachingTasks1 = teachingTaskMapper.selectTeachingTasksByName("课程教学任务");
        List<TeachingTask> allTeachingTasks2 = teachingTaskMapper.selectTeachingTasksByName("第1班");
        List<TeachingTask> allTeachingTasks3 = teachingTaskMapper.selectTeachingTasksByName("计算机课程_2");
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
    void selectTeachingTasksByCourseId() throws ParseException {
        List<TeachingTask> oldTeachingTasks = teachingTaskMapper.selectAllTeachingTasks();

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
            classGradeMapper.insertClass(classGrade);
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

            assertEquals(1, courseMapper.insertCourse(course));
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
            teacher.setUsername(teacher.getJobNo());
            teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
            teacher.setEnabled(true);
            Role role = roleMapper.getRoleByRoleName("teacher");
            assertNotNull(role);
            teacher.setRole(role);
            userMapper.insertUser(teacher);
            teacherMapper.insertTeacher(teacher);
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
                assertEquals(1, teachingTaskMapper.insertTeachingTask(teachingTask));
                assertNotNull(teachingTask.getId());
                teachingTasks.add(teachingTask);
                eCount++;
            }
        }
        assertEquals(30, eCount);
        List<TeachingTask> allTeachingTasks = teachingTaskMapper.selectAllTeachingTasks();
        for (Course course : courses) {
            List<TeachingTask> teachingTasksForCourse = teachingTaskMapper.selectTeachingTasksByCourseId(course.getId());
            assertEquals(5, teachingTasksForCourse.size());
            for (int i = oldTeachingTasks.size(); i < allTeachingTasks.size(); i++) {
                for (int j = 0; i < teachingTasksForCourse.size(); i++) {
                    if (allTeachingTasks.get(i).getId().longValue() == teachingTasksForCourse.get(j).getId().intValue()) {
                        assertEquals(allTeachingTasks.get(i), teachingTasksForCourse.get(j));
                    }
                }
            }
        }
    }

    @Test
    @Transactional
    @Rollback
    void selectTeachingTasksByTeacherId() throws ParseException {
        List<TeachingTask> oldTeachingTasks = teachingTaskMapper.selectAllTeachingTasks();

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
            classGradeMapper.insertClass(classGrade);
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

            assertEquals(1, courseMapper.insertCourse(course));
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
            teacher.setUsername(teacher.getJobNo());
            teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
            teacher.setEnabled(true);
            Role role = roleMapper.getRoleByRoleName("teacher");
            assertNotNull(role);
            teacher.setRole(role);
            userMapper.insertUser(teacher);
            teacherMapper.insertTeacher(teacher);
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
                assertEquals(1, teachingTaskMapper.insertTeachingTask(teachingTask));
                assertNotNull(teachingTask.getId());
                teachingTasks.add(teachingTask);
                eCount++;
            }
        }
        assertEquals(30, eCount);
        List<TeachingTask> allTeachingTasks = teachingTaskMapper.selectAllTeachingTasks();
        for (Teacher teacher : teachers) {
            List<TeachingTask> teachingTasksForTeacher = teachingTaskMapper.selectTeachingTasksByTeacherId(teacher.getId());
            assertEquals(5, teachingTasksForTeacher.size());
            for (int i = oldTeachingTasks.size(); i < allTeachingTasks.size(); i++) {
                for (int j = 0; i < teachingTasksForTeacher.size(); i++) {
                    if (allTeachingTasks.get(i).getId().longValue() == teachingTasksForTeacher.get(j).getId().intValue()) {
                        assertEquals(allTeachingTasks.get(i), teachingTasksForTeacher.get(j));
                    }
                }
            }
        }
    }

    @Test
    @Transactional
    @Rollback
    void selectTeachingTasksByClassGradeId() throws ParseException {
        List<TeachingTask> oldTeachingTasks = teachingTaskMapper.selectAllTeachingTasks();

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
            classGradeMapper.insertClass(classGrade);
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

            assertEquals(1, courseMapper.insertCourse(course));
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
            teacher.setUsername(teacher.getJobNo());
            teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
            teacher.setEnabled(true);
            Role role = roleMapper.getRoleByRoleName("teacher");
            assertNotNull(role);
            teacher.setRole(role);
            userMapper.insertUser(teacher);
            teacherMapper.insertTeacher(teacher);
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
                assertEquals(1, teachingTaskMapper.insertTeachingTask(teachingTask));
                assertNotNull(teachingTask.getId());
                teachingTasks.add(teachingTask);
                eCount++;
            }
        }
        assertEquals(30, eCount);
        List<TeachingTask> allTeachingTasks = teachingTaskMapper.selectAllTeachingTasks();
        for (ClassAndGrade classGrade : classGrades) {
            List<TeachingTask> teachingTasksForClassGrade = teachingTaskMapper.selectTeachingTasksByClassGradeId(classGrade.getId());
            assertEquals(6, teachingTasksForClassGrade.size());
            for (int i = oldTeachingTasks.size(); i < allTeachingTasks.size(); i++) {
                for (int j = 0; i < teachingTasksForClassGrade.size(); i++) {
                    if (allTeachingTasks.get(i).getId().longValue() == teachingTasksForClassGrade.get(j).getId().intValue()) {
                        assertEquals(allTeachingTasks.get(i), teachingTasksForClassGrade.get(j));
                    }
                }
            }
        }
    }

}
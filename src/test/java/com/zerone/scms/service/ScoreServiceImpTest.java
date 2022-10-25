package com.zerone.scms.service;

import com.zerone.scms.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScoreServiceImpTest {
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private ClassService classService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @Test
    @Transactional
    @Rollback
    void saveScore() {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertTrue(classService.saveClass(classGrade));

        //添加学生及用户
        Student student = new Student();
        student.setSno("stest001");
        student.setRealName("学生张");
        student.setClassAndGrade(classGrade);
        student.setGender("男");
        student.setAge(20);
        student.setContactNumber("11111111111");
        assertTrue(studentService.saveStudent(student));

        //添加课程
        Course course = new Course();
        course.setCno("test0001");
        course.setCname("测试课程");
        course.setPeriod(64);
        course.setCredit(2.0);
        assertTrue(courseService.saveCourse(course));

        //为学生添加课程成绩
        Score score = new Score();
        score.setStudent(student);
        score.setCourse(course);
        score.setAttendanceValue(60.0);
        score.setHomeworkValue(60.0);
        score.setQuizValue(60.0);
        score.setExamValue(60.0);
        assertTrue(scoreService.saveScore(score));
        Score newScore = scoreService.queryScoreById(score.getId());
        assertEquals(score, newScore);
    }

    @Test
    @Transactional
    @Rollback
    void saveScores() {
        List<Score> oldScores = scoreService.queryAllScores();
        //添加班级
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertTrue(classService.saveClass(classGrade));

        List<Course> courses = new ArrayList<Course>();
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCno("test000" + i);
            course.setCname("测试课程" + i);
            course.setPeriod(64);
            course.setCredit(2.0);
            assertTrue(courseService.saveCourse(course));
            courses.add(course);
        }
        List<Student> students = new ArrayList<Student>();
        for (int i = 0; i < 100; i++) {
            //添加学生
            Student student = new Student();
            student.setRealName("学生" + i);
            student.setClassAndGrade(classGrade);
            if (i < 10) {
                student.setSno("stest00" + i);
                student.setGender("男");
                student.setAge(21);
            } else {
                student.setSno("stest0" + i);
                student.setGender("女");
                student.setAge(20);
            }
            student.setContactNumber("11111111111");
            student.setUsername(student.getSno());
            student.setPassword(new BCryptPasswordEncoder().encode(student.getSno()));
            student.setEnabled(true);
            Role role = roleService.queryRoleByRoleName("student");
            student.setRole(role);
            userService.saveUser(student);
            students.add(student);
        }
        assertEquals(100, studentService.saveStudents(students));
        List<Score> scores = new ArrayList<Score>();
        for (Student student : students) {
            for (Course course : courses) {
                Score score = new Score();
                score.setStudent(student);
                score.setCourse(course);
                score.setAttendanceValue(60.0);
                score.setHomeworkValue(60.0);
                score.setQuizValue(60.0);
                score.setExamValue(60.0);
                scores.add(score);
            }
        }
        assertTrue(scoreService.saveScores(scores));

        //查询课程的学生成绩
        List<Score> allScores = scoreService.queryAllScores();
        int count = 0;
        for (Score score : scores) {
            for (int i = oldScores.size(); i < allScores.size(); i++) {
                if (score.getId().longValue() == allScores.get(i).getId().longValue()) {
                    assertEquals(score, allScores.get(i));
                    count++;
                }
            }
        }
        assertEquals(1000, count);
    }

    @Test
    @Transactional
    @Rollback
    void updateScore() {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertTrue(classService.saveClass(classGrade));

        //添加学生A
        Student studentA = new Student();
        studentA.setSno("stest001");
        studentA.setRealName("学生A");
        studentA.setClassAndGrade(classGrade);
        studentA.setGender("男");
        studentA.setAge(20);
        studentA.setContactNumber("11111111111");
        assertTrue(studentService.saveStudent(studentA));

        //添加学生B
        Student studentB = new Student();
        studentB.setSno("stest002");
        studentB.setRealName("学生B");
        studentB.setClassAndGrade(classGrade);
        studentB.setGender("男");
        studentB.setAge(20);
        studentB.setContactNumber("11111111111");
        assertTrue(studentService.saveStudent(studentB));

        //添加课程X
        Course courseX = new Course();
        courseX.setCno("test0001");
        courseX.setCname("测试课程1");
        courseX.setPeriod(64);
        courseX.setCredit(2.0);
        assertTrue(courseService.saveCourse(courseX));

        //添加课程Y
        Course courseY = new Course();
        courseY.setCno("test0002");
        courseY.setCname("测试课程2");
        courseY.setPeriod(64);
        courseY.setCredit(2.0);
        assertTrue(courseService.saveCourse(courseY));

        //添加成绩
        Score score = new Score();
        score.setStudent(studentA);
        score.setCourse(courseX);
        score.setAttendanceValue(60.0);
        score.setHomeworkValue(60.0);
        score.setQuizValue(60.0);
        score.setExamValue(60.0);
        assertTrue(scoreService.saveScore(score));
        Score newScore = scoreService.queryScoreById(score.getId());

        //修改成绩
        newScore.setStudent(studentB);
        newScore.setCourse(courseY);
        newScore.setAttendanceValue(70.0);
        newScore.setHomeworkValue(70.0);
        newScore.setQuizValue(70.0);
        newScore.setExamValue(70.0);
        assertTrue(scoreService.updateScore(newScore));
        Score lastScore = scoreService.queryScoreById(score.getId());
        assertEquals(newScore, lastScore);
    }

    @Test
    @Transactional
    @Rollback
    void deleteScore() {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertTrue(classService.saveClass(classGrade));

        //添加学生及用户
        Student student = new Student();
        student.setSno("stest001");
        student.setRealName("学生张");
        student.setClassAndGrade(classGrade);
        student.setGender("男");
        student.setAge(20);
        student.setContactNumber("11111111111");
        assertTrue(studentService.saveStudent(student));

        //添加课程
        Course course = new Course();
        course.setCno("test0001");
        course.setCname("测试课程");
        course.setPeriod(64);
        course.setCredit(2.0);
        assertTrue(courseService.saveCourse(course));

        //为学生添加课程成绩
        Score score = new Score();
        score.setStudent(student);
        score.setCourse(course);
        score.setAttendanceValue(60.0);
        score.setHomeworkValue(60.0);
        score.setQuizValue(60.0);
        score.setExamValue(60.0);
        assertTrue(scoreService.saveScore(score));
        Score newScore = scoreService.queryScoreById(score.getId());
        assertEquals(score, newScore);

        //删除课程成绩
        assertTrue(scoreService.deleteScore(newScore.getId()));
        Score lastScore = scoreService.queryScoreById(score.getId());
        assertNull(lastScore);
    }

    @Test
    @Transactional
    @Rollback
    void clearScore() {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertTrue(classService.saveClass(classGrade));

        //添加学生及用户
        Student student = new Student();
        student.setSno("stest001");
        student.setRealName("学生张");
        student.setClassAndGrade(classGrade);
        student.setGender("男");
        student.setAge(20);
        student.setContactNumber("11111111111");
        assertTrue(studentService.saveStudent(student));

        //添加课程
        Course course = new Course();
        course.setCno("test0001");
        course.setCname("测试课程");
        course.setPeriod(64);
        course.setCredit(2.0);
        assertTrue(courseService.saveCourse(course));

        //为学生添加课程成绩
        Score score = new Score();
        score.setStudent(student);
        score.setCourse(course);
        score.setAttendanceValue(60.0);
        score.setHomeworkValue(60.0);
        score.setQuizValue(60.0);
        score.setExamValue(60.0);
        assertTrue(scoreService.saveScore(score));
        Score newScore = scoreService.queryScoreById(score.getId());
        assertEquals(score, newScore);

        scoreService.clearScore(score.getId());
        Score lastScore = scoreService.queryScoreById(score.getId());
        newScore.setQuizValue(0.0);
        newScore.setExamValue(0.0);
        newScore.setHomeworkValue(0.0);
        newScore.setAttendanceValue(0.0);
        assertEquals(lastScore, newScore);
    }

    @Test
    @Transactional
    @Rollback
    void queryScoreById() {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertTrue(classService.saveClass(classGrade));

        //添加学生及用户
        Student student = new Student();
        student.setSno("stest001");
        student.setRealName("学生张");
        student.setClassAndGrade(classGrade);
        student.setGender("男");
        student.setAge(20);
        student.setContactNumber("11111111111");
        assertTrue(studentService.saveStudent(student));

        //添加课程
        Course course = new Course();
        course.setCno("test0001");
        course.setCname("测试课程");
        course.setPeriod(64);
        course.setCredit(2.0);
        assertTrue(courseService.saveCourse(course));

        //为学生添加课程成绩
        Score score = new Score();
        score.setStudent(student);
        score.setCourse(course);
        score.setAttendanceValue(60.0);
        score.setHomeworkValue(60.0);
        score.setQuizValue(60.0);
        score.setExamValue(60.0);
        assertTrue(scoreService.saveScore(score));
        Score newScore = scoreService.queryScoreById(score.getId());
        assertEquals(score, newScore);
    }

    @Test
    @Transactional
    @Rollback
    void queryScoresByStudentId() {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertTrue(classService.saveClass(classGrade));

        Student student = new Student();
        student.setRealName("学生");
        student.setClassAndGrade(classGrade);
        student.setSno("stest001");
        student.setGender("男");
        student.setAge(21);
        student.setContactNumber("11111111111");

        assertTrue(studentService.saveStudent(student));

        //添加课程
        List<Course> courses = new ArrayList<Course>();
        List<Score> scores = new ArrayList<Score>();
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCno("test000" + i);
            course.setCname("测试课程" + i);
            course.setPeriod(64);
            course.setCredit(2.0);
            assertTrue(courseService.saveCourse(course));

            //为学生添加课程成绩
            Score score = new Score();
            score.setStudent(student);
            score.setCourse(course);
            score.setAttendanceValue(60.0);
            score.setHomeworkValue(60.0);
            score.setQuizValue(60.0);
            score.setExamValue(60.0);
            scores.add(score);
        }

        assertTrue(scoreService.saveScores(scores));

        //查询课程的学生成绩
        List<Score> newScores = scoreService.queryScoresByStudentId(student.getId());
        int courseCount = 0;
        for (Score score : scores) {
            for (Score newScore : newScores) {
                if (score.getId().longValue() == newScore.getId().longValue()) {
                    assertEquals(score, newScore);
                    courseCount++;
                }
            }
        }
        assertEquals(10, courseCount);
    }

    @Test
    @Transactional
    @Rollback
    void queryScoresByCourseId() {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertTrue(classService.saveClass(classGrade));

        //添加课程
        Course course = new Course();
        course.setCno("test0001");
        course.setCname("测试课程");
        course.setPeriod(64);
        course.setCredit(2.0);
        assertTrue(courseService.saveCourse(course));

        List<Student> students = new ArrayList<Student>();
        List<Score> scores = new ArrayList<Score>();
        for (int i = 0; i < 100; i++) {
            //添加学生
            Student student = new Student();
            student.setRealName("学生" + i);
            student.setClassAndGrade(classGrade);
            if (i < 10) {
                student.setSno("stest00" + i);
                student.setGender("男");
                student.setAge(21);
            } else {
                student.setSno("stest0" + i);
                student.setGender("女");
                student.setAge(20);
            }
            student.setContactNumber("11111111111");
            student.setUsername(student.getSno());
            student.setPassword(new BCryptPasswordEncoder().encode(student.getSno()));
            student.setEnabled(true);
            Role role = roleService.queryRoleByRoleName("student");
            student.setRole(role);
            userService.saveUser(student);
            students.add(student);

            //为学生添加课程成绩
            Score score = new Score();
            score.setStudent(student);
            score.setCourse(course);
            score.setAttendanceValue(60.0);
            score.setHomeworkValue(60.0);
            score.setQuizValue(60.0);
            score.setExamValue(60.0);
            scores.add(score);
        }
        assertEquals(100, studentService.saveStudents(students));
        assertTrue(scoreService.saveScores(scores));

        //查询课程的学生成绩
        List<Score> newScores = scoreService.queryScoresByCourseId(course.getId());
        int stuCount = 0;
        for (Score score : scores) {
            for (Score newScore : newScores) {
                if (score.getId().longValue() == newScore.getId().longValue()) {
                    assertEquals(score, newScore);
                    stuCount++;
                }
            }
        }
        assertEquals(100, stuCount);
    }

    @Test
    @Transactional
    @Rollback
    void queryAllScores() {
        List<Score> oldScores = scoreService.queryAllScores();
        //添加班级
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertTrue(classService.saveClass(classGrade));

        //添加课程
        //添加课程
        List<Course> courses = new ArrayList<Course>();
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCno("test000" + i);
            course.setCname("测试课程" + i);
            course.setPeriod(64);
            course.setCredit(2.0);
            assertTrue(courseService.saveCourse(course));
            courses.add(course);
        }
        List<Student> students = new ArrayList<Student>();
        for (int i = 0; i < 100; i++) {
            //添加学生
            Student student = new Student();
            student.setRealName("学生" + i);
            student.setClassAndGrade(classGrade);
            if (i < 10) {
                student.setSno("stest00" + i);
                student.setGender("男");
                student.setAge(21);
            } else {
                student.setSno("stest0" + i);
                student.setGender("女");
                student.setAge(20);
            }
            student.setContactNumber("11111111111");
            students.add(student);
        }
        assertEquals(10, studentService.saveStudents(students));

        List<Score> scores = new ArrayList<Score>();
        for (Student student : students) {
            for (Course course : courses) {
                Score score = new Score();
                score.setStudent(student);
                score.setCourse(course);
                score.setAttendanceValue(60.0);
                score.setHomeworkValue(60.0);
                score.setQuizValue(60.0);
                score.setExamValue(60.0);
                scores.add(score);
            }
        }
        assertTrue(scoreService.saveScores(scores));

        //查询课程的学生成绩
        List<Score> allScores = scoreService.queryAllScores();
        int count = 0;
        for (Score score : scores) {
            for (int i = oldScores.size(); i < allScores.size(); i++) {
                if (score.getId().longValue() == allScores.get(i).getId().longValue()) {
                    assertEquals(score, allScores.get(i));
                    count++;
                }
            }
        }
        assertEquals(1000, count);
    }

    @Test
    @Transactional
    @Rollback
    void queryScoresByCourseAndStudentId() {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertTrue(classService.saveClass(classGrade));

        //添加学生及用户
        Student student = new Student();
        student.setSno("stest001");
        student.setRealName("学生张");
        student.setClassAndGrade(classGrade);
        student.setGender("男");
        student.setAge(20);
        student.setContactNumber("11111111111");
        assertTrue(studentService.saveStudent(student));

        //添加课程
        Course course = new Course();
        course.setCno("test0001");
        course.setCname("测试课程");
        course.setPeriod(64);
        course.setCredit(2.0);
        assertTrue(courseService.saveCourse(course));

        //为学生添加课程成绩
        Score score = new Score();
        score.setStudent(student);
        score.setCourse(course);
        score.setAttendanceValue(60.0);
        score.setHomeworkValue(60.0);
        score.setQuizValue(60.0);
        score.setExamValue(60.0);
        assertTrue(scoreService.saveScore(score));
        Score newScore = scoreService.queryScoresByCourseAndStudentId(student.getId(), course.getId());
        assertEquals(score, newScore);
    }
}
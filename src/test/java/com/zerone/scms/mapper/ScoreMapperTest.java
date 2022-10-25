package com.zerone.scms.mapper;

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
class ScoreMapperTest {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClassMapper classGradeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ScoreMapper scoreMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Test
    @Transactional
    @Rollback
    void insertScore() {
        //添加班级
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertEquals(1, classGradeMapper.insertClass(classGrade));

        //添加学生及用户
        Student student = new Student();
        student.setSno("stest001");
        student.setRealName("学生张");
        student.setClassAndGrade(classGrade);
        student.setGender("男");
        student.setAge(20);
        student.setContactNumber("11111111111");
        User user = (User) student;
        user.setUsername(student.getSno());
        user.setPassword(new BCryptPasswordEncoder().encode(student.getSno()));
        user.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("student");
        user.setRole(role);
        assertEquals(1, userMapper.insertUser(user));
        student.setUserId(user.getUserId());
        assertEquals(1, studentMapper.insertStudent(student));

        //添加课程
        Course course = new Course();
        course.setCno("test0001");
        course.setCname("测试课程");
        course.setPeriod(64);
        course.setCredit(2.0);
        assertEquals(1, courseMapper.insertCourse(course));

        //为学生添加课程成绩
        Score score = new Score();
        score.setStudent(student);
        score.setCourse(course);
        score.setAttendanceValue(60.0);
        score.setHomeworkValue(60.0);
        score.setQuizValue(60.0);
        score.setExamValue(60.0);
        assertEquals(1, scoreMapper.insertScore(score));
        Score newScore = scoreMapper.selectScoreById(score.getId());
        assertEquals(score, newScore);
    }

    @Test
    @Transactional
    @Rollback
    void insertScores() {
        List<Score> oldScores = scoreMapper.selectAllScores();
        //添加班级
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertEquals(1, classGradeMapper.insertClass(classGrade));

        //添加课程
        //添加课程
        List<Course> courses = new ArrayList<Course>();
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCno("test000" + i);
            course.setCname("测试课程" + i);
            course.setPeriod(64);
            course.setCredit(2.0);
            assertEquals(1, courseMapper.insertCourse(course));
            courses.add(course);
        }

        Role role = roleMapper.getRoleByRoleName("student");
        List<Student> students = new ArrayList<Student>();
        List<User> users = new ArrayList<User>();

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
            User user = (User) student;
            user.setUsername(student.getSno());
            user.setPassword(new BCryptPasswordEncoder().encode(student.getSno()));
            user.setEnabled(true);
            user.setRole(role);
            users.add(user);
            student.setUserId(user.getUserId());
            students.add(student);
        }
        assertEquals(100, userMapper.insertUsers(users));
        for (int i = 0; i < 100; i++) {
            students.get(i).setUserId(users.get(i).getUserId());
        }
        assertEquals(100, studentMapper.insertStudents(students));

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
        assertEquals(1000, scoreMapper.insertScores(scores));

        //查询课程的学生成绩
        List<Score> allScores = scoreMapper.selectAllScores();
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
        //添加班级
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertEquals(1, classGradeMapper.insertClass(classGrade));

        //添加学生A
        Role role = roleMapper.getRoleByRoleName("student");
        Student studentA = new Student();
        studentA.setSno("stest001");
        studentA.setRealName("学生A");
        studentA.setClassAndGrade(classGrade);
        studentA.setGender("男");
        studentA.setAge(20);
        studentA.setContactNumber("11111111111");
        User userA = (User) studentA;
        userA.setUsername(studentA.getSno());
        userA.setPassword(new BCryptPasswordEncoder().encode(studentA.getSno()));
        userA.setEnabled(true);
        userA.setRole(role);
        assertEquals(1, userMapper.insertUser(userA));
        studentA.setUserId(userA.getUserId());
        assertEquals(1, studentMapper.insertStudent(studentA));

        //添加学生B
        Student studentB = new Student();
        studentB.setSno("stest002");
        studentB.setRealName("学生B");
        studentB.setClassAndGrade(classGrade);
        studentB.setGender("男");
        studentB.setAge(20);
        studentB.setContactNumber("11111111111");
        User userB = (User) studentB;
        userB.setUsername(studentB.getSno());
        userB.setPassword(new BCryptPasswordEncoder().encode(studentB.getSno()));
        userB.setEnabled(true);
        userB.setRole(role);
        assertEquals(1, userMapper.insertUser(userB));
        studentB.setUserId(userB.getUserId());
        assertEquals(1, studentMapper.insertStudent(studentB));

        //添加课程X
        Course courseX = new Course();
        courseX.setCno("test0001");
        courseX.setCname("测试课程1");
        courseX.setPeriod(64);
        courseX.setCredit(2.0);
        assertEquals(1, courseMapper.insertCourse(courseX));

        //添加课程Y
        Course courseY = new Course();
        courseY.setCno("test0002");
        courseY.setCname("测试课程2");
        courseY.setPeriod(64);
        courseY.setCredit(2.0);
        assertEquals(1, courseMapper.insertCourse(courseY));

        //添加成绩
        Score score = new Score();
        score.setStudent(studentA);
        score.setCourse(courseX);
        score.setAttendanceValue(60.0);
        score.setHomeworkValue(60.0);
        score.setQuizValue(60.0);
        score.setExamValue(60.0);
        assertEquals(1, scoreMapper.insertScore(score));
        Score newScore = scoreMapper.selectScoreById(score.getId());

        //修改成绩
        newScore.setStudent(studentB);
        newScore.setCourse(courseY);
        newScore.setAttendanceValue(70.0);
        newScore.setHomeworkValue(70.0);
        newScore.setQuizValue(70.0);
        newScore.setExamValue(70.0);
        assertEquals(1, scoreMapper.updateScore(newScore));
        Score lastScore = scoreMapper.selectScoreById(score.getId());
        assertEquals(newScore, lastScore);
    }

    @Test
    @Transactional
    @Rollback
    void deleteScore() {
        //添加班级
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertEquals(1, classGradeMapper.insertClass(classGrade));

        //添加学生及用户
        Student student = new Student();
        student.setSno("stest001");
        student.setRealName("学生张");
        student.setClassAndGrade(classGrade);
        student.setGender("男");
        student.setAge(20);
        student.setContactNumber("11111111111");
        User user = (User) student;
        user.setUsername(student.getSno());
        user.setPassword(new BCryptPasswordEncoder().encode(student.getSno()));
        user.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("student");
        user.setRole(role);
        assertEquals(1, userMapper.insertUser(user));
        student.setUserId(user.getUserId());
        assertEquals(1, studentMapper.insertStudent(student));

        //添加课程
        Course course = new Course();
        course.setCno("test0001");
        course.setCname("测试课程");
        course.setPeriod(64);
        course.setCredit(2.0);
        assertEquals(1, courseMapper.insertCourse(course));

        //为学生添加课程成绩
        Score score = new Score();
        score.setStudent(student);
        score.setCourse(course);
        score.setAttendanceValue(60.0);
        score.setHomeworkValue(60.0);
        score.setQuizValue(60.0);
        score.setExamValue(60.0);
        assertEquals(1, scoreMapper.insertScore(score));
        Score newScore = scoreMapper.selectScoreById(score.getId());
        assertEquals(score, newScore);

        //删除课程成绩
        assertEquals(1, scoreMapper.deleteScore(newScore.getId()));
        Score lastScore = scoreMapper.selectScoreById(score.getId());
        assertNull(lastScore);

    }

    @Test
    @Transactional
    @Rollback
    void selectScoreById() {
        //添加班级
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertEquals(1, classGradeMapper.insertClass(classGrade));

        //添加学生及用户
        Student student = new Student();
        student.setSno("stest001");
        student.setRealName("学生张");
        student.setClassAndGrade(classGrade);
        student.setGender("男");
        student.setAge(20);
        student.setContactNumber("11111111111");
        User user = (User) student;
        user.setUsername(student.getSno());
        user.setPassword(new BCryptPasswordEncoder().encode(student.getSno()));
        user.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("student");
        user.setRole(role);
        assertEquals(1, userMapper.insertUser(user));
        student.setUserId(user.getUserId());
        assertEquals(1, studentMapper.insertStudent(student));

        //添加课程
        Course course = new Course();
        course.setCno("test0001");
        course.setCname("测试课程");
        course.setPeriod(64);
        course.setCredit(2.0);
        assertEquals(1, courseMapper.insertCourse(course));

        //为学生添加课程成绩
        Score score = new Score();
        score.setStudent(student);
        score.setCourse(course);
        score.setAttendanceValue(60.0);
        score.setHomeworkValue(60.0);
        score.setQuizValue(60.0);
        score.setExamValue(60.0);
        assertEquals(1, scoreMapper.insertScore(score));
        Score newScore = scoreMapper.selectScoreById(score.getId());
        assertEquals(score, newScore);

    }

    @Test
    @Transactional
    @Rollback
    void selectScoreByCourseId() {
        //添加班级
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertEquals(1, classGradeMapper.insertClass(classGrade));

        //添加课程
        Course course = new Course();
        course.setCno("test0001");
        course.setCname("测试课程");
        course.setPeriod(64);
        course.setCredit(2.0);
        assertEquals(1, courseMapper.insertCourse(course));

        Role role = roleMapper.getRoleByRoleName("student");
        List<Student> students = new ArrayList<Student>();
        List<User> users = new ArrayList<User>();
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
            User user = (User) student;
            user.setUsername(student.getSno());
            user.setPassword(new BCryptPasswordEncoder().encode(student.getSno()));
            user.setEnabled(true);
            user.setRole(role);
            users.add(user);
            student.setUserId(user.getUserId());
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
        assertEquals(100, userMapper.insertUsers(users));
        for (int i = 0; i < 100; i++) {
            students.get(i).setUserId(users.get(i).getUserId());
        }
        assertEquals(100, studentMapper.insertStudents(students));
        assertEquals(100, scoreMapper.insertScores(scores));

        //查询课程的学生成绩
        List<Score> newScores = scoreMapper.selectScoreByCourseId(course.getId());
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
    void selectScoreByStudentId() {
        //添加班级
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertEquals(1, classGradeMapper.insertClass(classGrade));

        Role role = roleMapper.getRoleByRoleName("student");
        ////添加学生
        Student student = new Student();
        student.setRealName("学生");
        student.setClassAndGrade(classGrade);
        student.setSno("stest001");
        student.setGender("男");
        student.setAge(21);
        student.setContactNumber("11111111111");
        User user = (User) student;
        user.setUsername(student.getSno());
        user.setPassword(new BCryptPasswordEncoder().encode(student.getSno()));
        user.setEnabled(true);
        user.setRole(role);
        assertEquals(1, userMapper.insertUser(user));
        student.setUserId(user.getUserId());
        assertEquals(1, studentMapper.insertStudent(student));

        //添加课程
        List<Course> courses = new ArrayList<Course>();
        List<Score> scores = new ArrayList<Score>();
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCno("test000" + i);
            course.setCname("测试课程" + i);
            course.setPeriod(64);
            course.setCredit(2.0);
            assertEquals(1, courseMapper.insertCourse(course));

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

        assertEquals(10, scoreMapper.insertScores(scores));

        //查询课程的学生成绩
        List<Score> newScores = scoreMapper.selectScoreByStudentId(student.getId());
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
    void selectScoreByStudentIdAndCourseId() {
        //添加班级
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertEquals(1, classGradeMapper.insertClass(classGrade));

        //添加学生及用户
        Student student = new Student();
        student.setSno("stest001");
        student.setRealName("学生张");
        student.setClassAndGrade(classGrade);
        student.setGender("男");
        student.setAge(20);
        student.setContactNumber("11111111111");
        User user = (User) student;
        user.setUsername(student.getSno());
        user.setPassword(new BCryptPasswordEncoder().encode(student.getSno()));
        user.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("student");
        user.setRole(role);
        assertEquals(1, userMapper.insertUser(user));
        student.setUserId(user.getUserId());
        assertEquals(1, studentMapper.insertStudent(student));

        //添加课程
        Course course = new Course();
        course.setCno("test0001");
        course.setCname("测试课程");
        course.setPeriod(64);
        course.setCredit(2.0);
        assertEquals(1, courseMapper.insertCourse(course));

        //为学生添加课程成绩
        Score score = new Score();
        score.setStudent(student);
        score.setCourse(course);
        score.setAttendanceValue(60.0);
        score.setHomeworkValue(60.0);
        score.setQuizValue(60.0);
        score.setExamValue(60.0);
        assertEquals(1, scoreMapper.insertScore(score));
        Score newScore = scoreMapper.selectScoreByStudentIdAndCourseId(student.getId(), course.getId());
        assertEquals(score, newScore);
    }

    @Test
    @Transactional
    @Rollback
    void selectAllScores() {
        List<Score> oldScores = scoreMapper.selectAllScores();
        //添加班级
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassName("测试班");
        classGrade.setClassNo("test0001");
        classGrade.setMajor("测试专业");
        classGrade.setGrade("2022");
        assertEquals(1, classGradeMapper.insertClass(classGrade));

        //添加课程
        //添加课程
        List<Course> courses = new ArrayList<Course>();
        for (int i = 0; i < 10; i++) {
            Course course = new Course();
            course.setCno("test000" + i);
            course.setCname("测试课程" + i);
            course.setPeriod(64);
            course.setCredit(2.0);
            assertEquals(1, courseMapper.insertCourse(course));
            courses.add(course);
        }

        Role role = roleMapper.getRoleByRoleName("student");
        List<Student> students = new ArrayList<Student>();
        List<User> users = new ArrayList<User>();

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
            User user = (User) student;
            user.setUsername(student.getSno());
            user.setPassword(new BCryptPasswordEncoder().encode(student.getSno()));
            user.setEnabled(true);
            user.setRole(role);
            users.add(user);
            student.setUserId(user.getUserId());
            students.add(student);
        }
        assertEquals(100, userMapper.insertUsers(users));
        for (int i = 0; i < 100; i++) {
            students.get(i).setUserId(users.get(i).getUserId());
        }
        assertEquals(100, studentMapper.insertStudents(students));

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
        assertEquals(1000, scoreMapper.insertScores(scores));

        //查询课程的学生成绩
        List<Score> allScores = scoreMapper.selectAllScores();
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

}
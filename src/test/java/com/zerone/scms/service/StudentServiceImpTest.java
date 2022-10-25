package com.zerone.scms.service;

import com.zerone.scms.mapper.ClassMapper;
import com.zerone.scms.mapper.RoleMapper;
import com.zerone.scms.mapper.StudentMapper;
import com.zerone.scms.mapper.UserMapper;
import com.zerone.scms.model.ClassAndGrade;
import com.zerone.scms.model.Role;
import com.zerone.scms.model.Student;
import com.zerone.scms.model.User;
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
class StudentServiceImpTest {
    @Autowired
    StudentService studentService;
    @Autowired
    ClassMapper classGradeMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    StudentMapper studentMapper;

    @Test
    @Transactional
    @Rollback(true)
    void saveStudent() {
        //新建课程对象并保存
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassNo("test0001");
        classGrade.setClassName("一班");
        classGrade.setGrade("2022");
        classGrade.setMajor("计算机科学与技术");
        assertEquals(1, classGradeMapper.insertClass(classGrade));
        //新建课程对象并保存

        //新建学生对象并保存
        Student stu = new Student();
        stu.setSno("stest001");
        stu.setRealName("张同学");
        stu.setGender("男");
        stu.setAge(20);
        stu.setContactNumber("11111111111");
        stu.setClassAndGrade(classGrade);
        Role role = roleMapper.getRoleByRoleName("student");

        stu.setUsername(stu.getSno());
        stu.setPassword(new BCryptPasswordEncoder().encode(stu.getSno()));
        stu.setRole(role);
        stu.setEnabled(true);
        stu.setRole(role);
        assertTrue(studentService.saveStudent(stu));
        assertNotNull(stu.getId());
        //新建学生对象并保存

        //通过id获取学生
        Student newStu = studentService.getStudentById(stu.getId());
        assertNotNull(newStu);
        assertEquals(stu, newStu);//判断id查询的学生和原来的学生是否相等
        User newUser = userMapper.selectUserByUserName(stu.getSno());//通过学号查询用户
        assertNotNull(newUser);//判断查询是否为空
        String psw = newUser.getPassword();//获取查询出来的用户的密码
        newUser.setPassword("");//newUser的密码设置为空以便于后面比较两个对像其他属性是否相等
        User eUser = new User();
        eUser.setUserId(newStu.getUserId());
        eUser.setUsername(stu.getUsername());
        eUser.setEnabled(true);
        eUser.setRole(role);
        eUser.setPassword("");
        assertEquals(eUser, newUser);//判断除密码外的其他属性是否相等
        assertTrue(new BCryptPasswordEncoder().matches(stu.getUsername(), psw));//判断获取出的密码和学生的姓名是否相等，如果相等则说明保存和查询成功

    }

    @Test
    @Transactional
    @Rollback(true)
    void getStudentById() {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassNo("test0001");
        classGrade.setClassName("第1班");
        classGrade.setGrade("2022");
        classGrade.setMajor("计算机科学与技术");
        assertEquals(1, classGradeMapper.insertClass(classGrade));
        Student stu = new Student();
        stu.setSno("stest001");
        stu.setRealName("张同学");
        stu.setGender("男");
        stu.setAge(20);
        stu.setContactNumber("11111111111");
        stu.setClassAndGrade(classGrade);
        Role role = roleMapper.getRoleByRoleName("student");
        User user = (User) stu;
        user.setUsername(stu.getSno());
        user.setPassword(new BCryptPasswordEncoder().encode(stu.getSno()));
        user.setRole(role);
        user.setEnabled(true);
        user.setRole(role);
        stu.setUserId(user.getUserId());
        assertTrue(studentService.saveStudent(stu));
        assertNotNull(stu.getId());
        Student newStu = studentService.getStudentById(stu.getId());
        assertNotNull(newStu);
        assertEquals(stu, newStu);
        User newUser = userMapper.selectUserByUserName(stu.getSno());
        assertNotNull(newUser);
        String psw = newUser.getPassword();
        newUser.setPassword("");
        User eUser = new User();
        eUser.setUserId(newStu.getUserId());
        eUser.setUsername(user.getUsername());
        eUser.setEnabled(true);
        eUser.setRole(role);
        eUser.setPassword("");
        assertEquals(eUser, newUser);
        assertTrue(new BCryptPasswordEncoder().matches(user.getUsername(), psw));
    }

    @Test
    @Transactional
    @Rollback(true)
    void getAllStudents() {
        List<Student> oldStudents = studentService.getAllStudents();
        List<Student> students = new ArrayList<Student>();
        List<User> users = new ArrayList<User>();
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassNo("test0001");
        classGrade.setClassName("一班");
        classGrade.setGrade("2022");
        classGrade.setMajor("计算机科学与技术");
        assertEquals(1, classGradeMapper.insertClass(classGrade));
        assertNotNull(classGrade.getId());
        for (int i = 0; i < 100; i++) {
            Student stu = new Student();
            if (i < 10) {
                stu.setSno("stest00" + i);
            } else {
                stu.setSno("stest0" + i);
            }
            stu.setRealName("同学" + i);
            stu.setGender("男");
            stu.setAge(20);
            stu.setContactNumber("11111111111");
            stu.setClassAndGrade(classGrade);
            Role role = roleMapper.getRoleByRoleName("student");
            User user = (User) stu;
            user.setUsername(stu.getSno());
            user.setPassword(new BCryptPasswordEncoder().encode(stu.getSno()));
            user.setRole(role);
            user.setEnabled(true);
            users.add(user);
            students.add(stu);
        }
        assertEquals(100, userMapper.insertUsers(users));
        for (int i = 0; i < 100; i++) {
            students.get(i).setUserId(users.get(i).getUserId());
        }
        assertEquals(100, studentService.saveStudents(students));
        List<Student> allStudents = studentService.getAllStudents();
        int count = 0;
        for (Student student : students) {
            for (int i = oldStudents.size(); i < allStudents.size(); i++) {
                if (student.getId().longValue() == allStudents.get(i).getId().longValue()) {
                    assertEquals(student, allStudents.get(i));
                    User user = userMapper.selectUserByUserName(student.getSno());
                    assertTrue(user.getEnabled());
                    assertTrue(new BCryptPasswordEncoder().matches(user.getUsername(), user.getPassword()));
                    count++;
                }
            }
        }
        assertEquals(100, count);
    }

    @Test
    @Transactional
    @Rollback(true)
    void selectStudentsByRealName() {
        List<Student> oldStudents1 = studentService.selectStudentsByRealName("同学");
        List<Student> oldStudents2 = studentService.selectStudentsByRealName("15");
        List<Student> students = new ArrayList<Student>();
        List<User> users = new ArrayList<User>();
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassNo("test0001");
        classGrade.setClassName("一班");
        classGrade.setGrade("2022");
        classGrade.setMajor("计算机科学与技术");
        assertEquals(1, classGradeMapper.insertClass(classGrade));
        assertNotNull(classGrade.getId());
        for (int i = 0; i < 100; i++) {
            Student stu = new Student();
            if (i < 10) {
                stu.setSno("stest00" + i);
            } else {
                stu.setSno("stest0" + i);
            }
            stu.setRealName("同学" + i);
            stu.setGender("男");
            stu.setAge(20);
            stu.setContactNumber("11111111111");
            stu.setClassAndGrade(classGrade);
            Role role = roleMapper.getRoleByRoleName("student");

            stu.setUsername(stu.getSno());
            stu.setPassword(new BCryptPasswordEncoder().encode(stu.getSno()));
            stu.setRole(role);
            stu.setEnabled(true);
            users.add(stu);
            students.add(stu);
        }
        assertEquals(100, userMapper.insertUsers(users));
        for (int i = 0; i < 100; i++) {
            students.get(i).setUserId(users.get(i).getUserId());
        }
        assertEquals(100, studentMapper.insertStudents(students));
        List<Student> allStudents1 = studentService.selectStudentsByRealName("同学");
        List<Student> allStudents2 = studentService.selectStudentsByRealName("15");
        int count1 = 0;
        int count2 = 0;
        for (Student student : students) {
            for (int i = oldStudents1.size(); i < allStudents1.size(); i++) {
                if (student.getId().longValue() == allStudents1.get(i).getId().longValue()) {
                    assertEquals(student, allStudents1.get(i));
                    User user = userMapper.selectUserByUserName(student.getSno());
                    assertTrue(user.getEnabled());
                    assertTrue(new BCryptPasswordEncoder().matches(user.getUsername(), user.getPassword()));
                    count1++;
                }
            }
            for (int i = oldStudents2.size(); i < allStudents2.size(); i++) {
                if (student.getId().longValue() == allStudents2.get(i).getId().longValue()) {
                    assertEquals(student, allStudents2.get(i));
                    User user = userMapper.selectUserByUserName(student.getSno());
                    assertTrue(user.getEnabled());
                    assertTrue(new BCryptPasswordEncoder().matches(user.getUsername(), user.getPassword()));
                    count2++;
                }
            }
        }
        assertEquals(100, count1);
        assertEquals(1, count2);
    }

    @Test
    @Transactional
    @Rollback(true)
    void selectStudentBySno() {
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassNo("test0001");
        classGrade.setClassName("一班");
        classGrade.setGrade("2022");
        classGrade.setMajor("计算机科学与技术");
        assertEquals(1, classGradeMapper.insertClass(classGrade));
        Student stu = new Student();
        stu.setSno("stest001");
        stu.setRealName("张同学");
        stu.setGender("男");
        stu.setAge(20);
        stu.setContactNumber("11111111111");
        stu.setClassAndGrade(classGrade);
        Role role = roleMapper.getRoleByRoleName("student");
        stu.setUsername(stu.getSno());
        stu.setPassword(new BCryptPasswordEncoder().encode(stu.getSno()));
        stu.setRole(role);
        stu.setEnabled(true);
        stu.setRole(role);
        assertTrue(studentService.saveStudent(stu));
        assertNotNull(stu.getId());
        Student newStu = studentMapper.selectStudentBySno(stu.getSno());
        assertNotNull(newStu);
        assertEquals(stu, newStu);
        User newUser = userMapper.selectUserByUserName(stu.getSno());
        assertNotNull(newUser);
        String psw = newUser.getPassword();
        newUser.setPassword("");
        User eUser = new User();
        eUser.setUserId(newStu.getUserId());
        eUser.setUsername(stu.getUsername());
        eUser.setEnabled(true);
        eUser.setRole(role);
        eUser.setPassword("");
        assertEquals(eUser, newUser);
        assertTrue(new BCryptPasswordEncoder().matches(stu.getUsername(), psw));
    }

    @Test
    @Transactional
    @Rollback(true)
    void deleteStudent() {
        List<Student> students = studentService.selectStudentsByRealName("李");
        assertEquals(1, students.size());

        studentService.deleteStudent(students.get(0).getId());

        List<Student> studentsNew = studentService.selectStudentsByRealName("李");
        assertEquals(0, studentsNew.size());
    }

    @Test
    @Transactional
    @Rollback(true)
    void validateStudent() {
        Student student = new Student();
//        必须为11位
        student.setSno("20190081110");
//        不为空
        student.setRealName("李岩青");
//        不为空实际是一个单选框
        student.setGender("男");
//        不为空
        student.setAge(23);
//        不为空且必须为11位
        student.setContactNumber("183****5801");
//        在实际中需要从数据库中先获取相应的班级id再放进来，此处先用来测试
//        不可为空
        student.setClassGradesId(3L);

        assertTrue(studentService.validateStudent(student));
    }
}
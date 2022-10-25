package com.zerone.scms.mapper;

import com.zerone.scms.model.ClassAndGrade;
import com.zerone.scms.model.Role;
import com.zerone.scms.model.Student;
import com.zerone.scms.model.User;
import com.zerone.scms.service.UserService;
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
class StudentMapperTest {
    @Autowired
    ClassMapper classGradeMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserService userService;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    UserMapper userMapper;

    @Test
    @Transactional
    @Rollback
    void insertStudent() {
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
        userService.saveUser(stu);
        assertNotNull(stu.getUserId());
        assertEquals(1, studentMapper.insertStudent(stu));
        assertNotNull(stu.getId());
        Student newStu = studentMapper.selectStudentById(stu.getId());
        assertNotNull(newStu);
        assertEquals(stu, newStu);
        User newUser = userService.queryUserByUserName(stu.getSno());
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
    @Rollback
    void insertStudents() {
        List<Student> oldStudents = studentMapper.selectAllStudents();
        List<Student> students = new ArrayList<Student>();
        List<User> users = new ArrayList<User>();
        ClassAndGrade classGrade = new ClassAndGrade();
        classGrade.setClassNo("test0001");
        classGrade.setClassName("第1班");
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
        assertEquals(100, studentMapper.insertStudents(students));
        List<Student> allStudents = studentMapper.selectAllStudents();
        int count = 0;
        for (Student student : students) {
            for (int i = oldStudents.size(); i < allStudents.size(); i++) {
                if (student.getId().longValue() == allStudents.get(i).getId().longValue()) {
                    assertEquals(student, allStudents.get(i));
                    User user = userService.queryUserByUserName(student.getSno());
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
    @Rollback
    void selectStudentById() {
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
        userService.saveUser(user);
        assertNotNull(user.getUserId());
        stu.setUserId(user.getUserId());
        assertEquals(1, studentMapper.insertStudent(stu));
        assertNotNull(stu.getId());
        Student newStu = studentMapper.selectStudentById(stu.getId());
        assertNotNull(newStu);
        assertEquals(stu, newStu);
        User newUser = userService.queryUserByUserName(stu.getSno());
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
    @Rollback
    void selectAllStudents() {
        List<Student> oldStudents = studentMapper.selectAllStudents();
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
        assertEquals(100, studentMapper.insertStudents(students));
        List<Student> allStudents = studentMapper.selectAllStudents();
        int count = 0;
        for (Student student : students) {
            for (int i = oldStudents.size(); i < allStudents.size(); i++) {
                if (student.getId().longValue() == allStudents.get(i).getId().longValue()) {
                    assertEquals(student, allStudents.get(i));
                    User user = userService.queryUserByUserName(student.getSno());
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
    @Rollback
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
        userService.saveUser(stu);
        assertNotNull(stu.getUserId());
        assertEquals(1, studentMapper.insertStudent(stu));
        assertNotNull(stu.getId());
        Student newStu = studentMapper.selectStudentBySno(stu.getSno());
        assertNotNull(newStu);
        assertEquals(stu, newStu);
        User newUser = userService.queryUserByUserName(stu.getSno());
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
    @Rollback
    void selectStudentsByName() {
        List<Student> oldStudents1 = studentMapper.selectStudentsByRealName("同学");
        List<Student> oldStudents2 = studentMapper.selectStudentsByRealName("15");
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
        List<Student> allStudents1 = studentMapper.selectStudentsByRealName("同学");
        List<Student> allStudents2 = studentMapper.selectStudentsByRealName("15");
        int count1 = 0;
        int count2 = 0;
        for (Student student : students) {
            for (int i = oldStudents1.size(); i < allStudents1.size(); i++) {
                if (student.getId().longValue() == allStudents1.get(i).getId().longValue()) {
                    assertEquals(student, allStudents1.get(i));
                    User user = userService.queryUserByUserName(student.getSno());
                    assertTrue(user.getEnabled());
                    assertTrue(new BCryptPasswordEncoder().matches(user.getUsername(), user.getPassword()));
                    count1++;
                }
            }
            for (int i = oldStudents2.size(); i < allStudents2.size(); i++) {
                if (student.getId().longValue() == allStudents2.get(i).getId().longValue()) {
                    assertEquals(student, allStudents2.get(i));
                    User user = userService.queryUserByUserName(student.getSno());
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
    @Rollback
    void selectStudentsByClassGradeId() {
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
        List<Student> newStudents = studentMapper.selectStudentsByClassGradeId(classGrade.getId());
        int count = 0;
        for (Student student : students) {
            for (int i = 0; i < newStudents.size(); i++) {
                if (student.getId().longValue() == newStudents.get(i).getId().longValue()) {
                    assertEquals(student, newStudents.get(i));
                    User user = userService.queryUserByUserName(student.getSno());
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
    @Rollback
    void updateStudent() {
        ClassAndGrade classGrade1 = new ClassAndGrade();
        classGrade1.setClassNo("test0001");
        classGrade1.setClassName("一班");
        classGrade1.setGrade("2022");
        classGrade1.setMajor("计算机科学与技术");
        assertEquals(1, classGradeMapper.insertClass(classGrade1));
        ClassAndGrade classGrade2 = new ClassAndGrade();
        classGrade2.setClassNo("test0002");
        classGrade2.setClassName("二班");
        classGrade2.setGrade("2022");
        classGrade2.setMajor("计算机科学与技术");
        assertEquals(1, classGradeMapper.insertClass(classGrade2));
        Student stu = new Student();
        stu.setSno("stest001");
        stu.setRealName("张同学");
        stu.setGender("男");
        stu.setAge(20);
        stu.setContactNumber("11111111111");
        stu.setClassAndGrade(classGrade1);
        Role role = roleMapper.getRoleByRoleName("student");

        stu.setUsername(stu.getSno());
        stu.setPassword(new BCryptPasswordEncoder().encode(stu.getSno()));
        stu.setRole(role);
        stu.setEnabled(true);
        stu.setRole(role);
        userService.saveUser(stu);
        assertNotNull(stu.getUserId());

        assertEquals(1, studentMapper.insertStudent(stu));
        assertNotNull(stu.getId());
        Student newStu = studentMapper.selectStudentById(stu.getId());
        assertNotNull(newStu);
        assertEquals(stu, newStu);

        newStu.setRealName("王同学");
        newStu.setContactNumber("22222222222");
        newStu.setAge(30);
        newStu.setGender("女");
        newStu.setClassAndGrade(classGrade2);
        assertEquals(1, studentMapper.updateStudent(newStu));
        Student lastStu = studentMapper.selectStudentById(stu.getId());
        assertEquals(newStu, lastStu);
    }

}
package com.zerone.scms.mapper;

import com.zerone.scms.model.Role;
import com.zerone.scms.model.Teacher;
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
class TeacherMapperTest {
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;

    @Test
    @Transactional
    @Rollback
    void insertTeacher() {
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

        assertEquals(1, teacherMapper.insertTeacher(teacher));
        assertNotNull(teacher.getId());
        Teacher newTeacher = teacherMapper.selectTeacherById(teacher.getId());
        assertEquals(teacher, newTeacher);
        User newUser = userMapper.selectUserByUserName(teacher.getJobNo());
        User eUser = new User();
        eUser.setUserId(teacher.getUserId());
        eUser.setUsername(teacher.getUsername());
        eUser.setPassword(teacher.getPassword());
        eUser.setRole(teacher.getRole());
        eUser.setEnabled(teacher.getEnabled());
        assertEquals(eUser, newUser);
        assertTrue(new BCryptPasswordEncoder().matches(teacher.getUsername(), newUser.getPassword()));
    }

    @Test
    @Transactional
    @Rollback
    void updateTeacher() {
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

        assertEquals(1, teacherMapper.insertTeacher(teacher));
        assertNotNull(teacher.getId());
        Teacher newTeacher = teacherMapper.selectTeacherById(teacher.getId());
        newTeacher.setRealName("张老师");
        newTeacher.setContactNumber("11111111112");
        newTeacher.setMajor("网络工程");
        newTeacher.setGender("女");
        newTeacher.setAge(40);
        assertEquals(1, teacherMapper.updateTeacher(newTeacher));
        Teacher lastTeacher = teacherMapper.selectTeacherById(teacher.getId());
        assertEquals(newTeacher, lastTeacher);
    }

    @Test
    @Transactional
    @Rollback
    void deleteTeacher() {
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

        assertEquals(1, teacherMapper.insertTeacher(teacher));
        assertNotNull(teacher.getId());
        Teacher newTeacher = teacherMapper.selectTeacherById(teacher.getId());
        assertNotNull(newTeacher);
        assertEquals(1, teacherMapper.deleteTeacher(teacher.getId()));
        Teacher lastTeacher = teacherMapper.selectTeacherById(teacher.getId());
        assertNull(lastTeacher);
    }

    @Test
    @Transactional
    @Rollback
    void selectTeachersByRealName() {
        List<Teacher> oldTeachers1 = teacherMapper.selectTeachersByRealName("教师");
        List<Teacher> oldTeachers2 = teacherMapper.selectTeachersByRealName("教师5");
        List<Teacher> teachers = new ArrayList<Teacher>();
        for (int i = 0; i < 10; i++) {
            Teacher teacher = new Teacher();
            teacher.setJobNo("ttest00" + i);
            teacher.setRealName("教师" + i);
            teacher.setContactNumber("1111111111" + i);
            if (i % 2 == 0) {
                teacher.setGender("男");
                teacher.setAge(30);
                teacher.setMajor("网络工程");
            } else {
                teacher.setGender("女");
                teacher.setAge(40);
                teacher.setMajor("计算机科学与技术");
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
        List<Teacher> allTeachers1 = teacherMapper.selectTeachersByRealName("教师");
        List<Teacher> allTeachers2 = teacherMapper.selectTeachersByRealName("教师5");
        int count1 = 0;
        int count2 = 0;
        for (Teacher teacher : teachers) {
            for (int i = oldTeachers1.size(); i < allTeachers1.size(); i++) {
                if (teacher.getId().longValue() == allTeachers1.get(i).getId().longValue()) {
                    assertEquals(teacher, allTeachers1.get(i));
                    count1++;
                }
            }
            for (int i = oldTeachers2.size(); i < allTeachers2.size(); i++) {
                if (teacher.getId().longValue() == allTeachers2.get(i).getId().longValue()) {
                    assertEquals(teacher, allTeachers2.get(i));
                    count2++;
                }
            }
        }
        assertEquals(10, count1);
        assertEquals(1, count2);
    }

    @Test
    @Transactional
    @Rollback
    void selectAllTeachers() {
        List<Teacher> oldTeachers = teacherMapper.selectAllTeachers();
        List<Teacher> teachers = new ArrayList<Teacher>();
        for (int i = 0; i < 10; i++) {
            Teacher teacher = new Teacher();
            teacher.setJobNo("ttest00" + i);
            teacher.setRealName("教师" + i);
            teacher.setContactNumber("1111111111" + i);
            if (i % 2 == 0) {
                teacher.setGender("男");
                teacher.setAge(30);
                teacher.setMajor("网络工程");
            } else {
                teacher.setGender("女");
                teacher.setAge(40);
                teacher.setMajor("计算机科学与技术");
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
        List<Teacher> allTeachers = teacherMapper.selectAllTeachers();
        int count = 0;
        for (Teacher teacher : teachers) {
            for (int i = oldTeachers.size(); i < allTeachers.size(); i++) {
                if (teacher.getId().longValue() == allTeachers.get(i).getId().longValue()) {
                    assertEquals(teacher, allTeachers.get(i));
                    count++;
                }
            }
        }
        assertEquals(10, count);
    }

    @Test
    @Transactional
    @Rollback
    void selectTeacherById() {
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

        assertEquals(1, teacherMapper.insertTeacher(teacher));
        assertNotNull(teacher.getId());
        Teacher newTeacher = teacherMapper.selectTeacherById(teacher.getId());
        assertEquals(teacher, newTeacher);
        User newUser = userMapper.selectUserByUserName(teacher.getJobNo());
        User eUser = new User();
        eUser.setUserId(teacher.getUserId());
        eUser.setUsername(teacher.getUsername());
        eUser.setPassword(teacher.getPassword());
        eUser.setRole(teacher.getRole());
        eUser.setEnabled(teacher.getEnabled());
        assertEquals(eUser, newUser);
        assertTrue(new BCryptPasswordEncoder().matches(teacher.getUsername(), newUser.getPassword()));
    }

    @Test
    @Transactional
    @Rollback
    void selectTeacherByJobNo() {
        Teacher teacher = new Teacher();
        teacher.setJobNo("ttest001");
        teacher.setRealName("教师张");
        teacher.setGender("男");
        teacher.setAge(30);
        teacher.setMajor("计算机科学与技术");
        teacher.setContactNumber("11111111111");

        User user = (User) teacher;
        teacher.setUsername(teacher.getJobNo());
        teacher.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
        teacher.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("teacher");
        assertNotNull(role);
        teacher.setRole(role);
        userMapper.insertUser(user);

        assertEquals(1, teacherMapper.insertTeacher(teacher));
        assertNotNull(teacher.getId());
        Teacher newTeacher = teacherMapper.selectTeacherByJobNo(teacher.getJobNo());
        assertEquals(teacher, newTeacher);
        User newUser = userMapper.selectUserByUserName(teacher.getJobNo());
        User eUser = new User();
        eUser.setUserId(teacher.getUserId());
        eUser.setUsername(user.getUsername());
        eUser.setPassword(user.getPassword());
        eUser.setRole(user.getRole());
        eUser.setEnabled(user.getEnabled());
        assertEquals(eUser, newUser);
        assertTrue(new BCryptPasswordEncoder().matches(user.getUsername(), newUser.getPassword()));
    }
}
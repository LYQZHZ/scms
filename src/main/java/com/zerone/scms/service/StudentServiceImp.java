package com.zerone.scms.service;

import com.zerone.scms.mapper.StudentMapper;
import com.zerone.scms.mapper.UserMapper;
import com.zerone.scms.mapper.UserRoleMapper;
import com.zerone.scms.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("studentService")

public class StudentServiceImp implements StudentService {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;
    @Autowired
    ClassService classService;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public boolean saveStudent(Student student) {
        Boolean result = true;
        Long id = student.getId();
        if (null == id) {
            User studentUser = student;
            studentUser.setUsername(student.getSno());
            studentUser.setPassword(new BCryptPasswordEncoder().encode(student.getSno()));
            studentUser.setEnabled(true);
            Role role = roleService.queryRoleByRoleName("student");
            studentUser.setRole(role);
            if (!userService.saveUser(studentUser)) {
                result = false;
                throw new RuntimeException("用户信息保存失败1！");
            }

            student.setUserId(studentUser.getUserId());
            if (1 != studentMapper.insertStudent(student)) {
                result = false;
                throw new RuntimeException("学生信息保存失败！");
            }

        } else {
            if (studentMapper.updateStudent(student) == 1) {
                User user = userMapper.selectUserByUserId(student.getUserId());
                user.setUsername(student.getSno());
                user.setPassword(new BCryptPasswordEncoder().encode(student.getSno()));
                if (userMapper.updateUser(user) != 1) {
                    result = false;
                    throw new RuntimeException("用户信息保存失败2！");
                }

            } else {
                result = false;
                throw new RuntimeException("学生信息保存失败2！");
            }
        }
        return result;
    }

    @Override
    public int saveStudents(List<Student> students) {
        
        return studentMapper.insertStudents(students);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentMapper.selectStudentById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentMapper.selectAllStudents();
    }

    @Override
    public List<Student> selectStudentsByRealName(String realName) {
        return studentMapper.selectStudentsByRealName(realName);
    }

    @Override
    public Student selectStudentBySno(String sno) {
        return studentMapper.selectStudentBySno(sno);
    }

    @Override
    public boolean deleteStudent(Long id) {
        Boolean result = true;
        Student student = studentMapper.selectStudentById(id);
        if (student != null) {
            if (userRoleMapper.deleteUserRoleByUserId(student.getUserId()) != 1) {
                result = false;
                throw new RuntimeException("删除学生对应的用户角色失败！");
            }
            if (studentMapper.deleteStudent(id) != 1) {
                result = false;
                throw new RuntimeException("删除学生信息失败！");
            }
            User user = userMapper.selectUserByUserId(student.getUserId());
            if (userMapper.deleteUserByUserId(student.getUserId()) != 1) {
                result = false;
                throw new RuntimeException("删除学生对应用户失败！");
            }
        } else {
            result = false;
            throw new RuntimeException("删除的学生信息不存在！");
        }
        return result;
    }

    @Override
    public boolean validateStudent(Student student) {
        boolean result = true;
        if (student.getSno() == null || student.getSno().length() != 11) {
            result = false;
        }
        if (student.getRealName() == null || student.getRealName().length() == 0) {
            result = false;
        }
        if (student.getGender() == null || student.getGender().length() != 1) {
            result = false;
        }
        if (student.getAge() == null || student.getAge() < 0) {
            result = false;
        }
        if (student.getContactNumber() == null || student.getContactNumber().length() != 11) {
            result = false;
        }
        if (student.getClassGradesId() == null) {
            result = false;
        }
        if (student.getId() != null) {
            if (student.getUserId() == null) {
                result = false;
            }
        } else {
            if (student.getUserId() != null) {
                result = false;
            }
        }
        return result;
    }


}

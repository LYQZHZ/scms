package com.zerone.scms.service;

import com.zerone.scms.mapper.RoleMapper;
import com.zerone.scms.mapper.TeacherMapper;
import com.zerone.scms.mapper.UserMapper;
import com.zerone.scms.mapper.UserRoleMapper;
import com.zerone.scms.model.Role;
import com.zerone.scms.model.Teacher;
import com.zerone.scms.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("teacherService")
public class TeacherServiceImp implements TeacherService {
    Logger logger = LoggerFactory.getLogger(TeacherServiceImp.class);
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public boolean saveTeacher(Teacher teacher) {
        Boolean result = true;
        Long id = teacher.getId();
        if (null == id) {
            User teacherUser = teacher;
            teacherUser.setUsername(teacher.getJobNo());
            teacherUser.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
            teacherUser.setEnabled(true);
            Role role = roleMapper.getRoleByRoleName("teacher");
            teacherUser.setRole(role);
            if (userMapper.insertUser(teacherUser) != 1) {
                result = false;
                throw new RuntimeException("用户信息保存失败1！");
            }

            teacher.setUserId(teacherUser.getUserId());
            if (1 != teacherMapper.insertTeacher(teacher)) {
                result = false;
                throw new RuntimeException("教师信息保存失败！");
            }

        } else {
            if (teacherMapper.updateTeacher(teacher) == 1) {
                User user = userMapper.selectUserByUserId(teacher.getUserId());
                user.setUsername(teacher.getJobNo());
                user.setPassword(new BCryptPasswordEncoder().encode(teacher.getJobNo()));
                if (userMapper.updateUser(user) != 1) {
                    result = false;
                    throw new RuntimeException("用户信息保存失败2！");
                }

            } else {
                result = false;
                throw new RuntimeException("教师信息保存失败！");
            }
        }
        return result;
    }


    @Override
    public Teacher getTeacherById(Long id) {
        return teacherMapper.selectTeacherById(id);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherMapper.selectAllTeachers();
    }

    @Override
    public List<Teacher> selectTeachersByRealName(String realName) {
        return teacherMapper.selectTeachersByRealName(realName);
    }

    @Override
    public Teacher selectTeacherByJobNo(String jobNo) {
        return teacherMapper.selectTeacherByJobNo(jobNo);
    }

    @Override
    public boolean deleteTeacher(Long id) {
        Boolean result = true;
        Teacher teacher = teacherMapper.selectTeacherById(id);
        if (teacher != null) {

            if (teacherMapper.deleteTeacher(id) != 1) {
                result = false;
                throw new RuntimeException("删除教师失败！");
            }
            User user = userMapper.selectUserByUserId(teacher.getUserId());
            if (userMapper.deleteUserByUserId(teacher.getUserId()) != 1) {
                result = false;
                throw new RuntimeException("删除教师对应用户失败！");
            }
        } else {
            result = false;
            throw new RuntimeException("删除的教师不存在！");
        }
        return result;
    }

    @Override
    public boolean validateTeacher(Teacher teacher) {
        boolean result = true;
        if (teacher.getJobNo() == null || teacher.getJobNo().length() != 8) {
            result = false;
        }
        if (teacher.getRealName() == null || teacher.getRealName().length() == 0) {
            result = false;
        }
        if (teacher.getGender() == null || teacher.getGender().length() != 1) {
            result = false;
        }
        if (teacher.getAge() == null || teacher.getAge() < 0) {
            result = false;
        }
        if (teacher.getContactNumber() == null || teacher.getContactNumber().length() != 11) {
            result = false;
        }
        if (teacher.getMajor() == null || teacher.getMajor().length() == 0) {
            result = false;
        }
        if (teacher.getId() != null) {
            if (teacher.getUserId() == null) {
                result = false;
            }
        } else {
            if (teacher.getUserId() != null) {
                result = false;
            }
        }
        return result;
    }
}

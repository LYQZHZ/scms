package com.zerone.scms.service;

import com.zerone.scms.model.Teacher;

import java.util.List;

public interface TeacherService {

    boolean saveTeacher(Teacher teacher);

    Teacher getTeacherById(Long id);

    List<Teacher> getAllTeachers();

    List<Teacher> selectTeachersByRealName(String realName);

    Teacher selectTeacherByJobNo(String jobNo);

    boolean deleteTeacher(Long id);

    boolean validateTeacher(Teacher teacher);
}

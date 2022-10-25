package com.zerone.scms.service;

import com.zerone.scms.model.Student;

import java.util.List;

public interface StudentService {
    boolean saveStudent(Student student);

    int saveStudents(List<Student> students);

    Student getStudentById(Long id);

    List<Student> getAllStudents();

    List<Student> selectStudentsByRealName(String realName);

    Student selectStudentBySno(String sno);

    boolean deleteStudent(Long id);

    boolean validateStudent(Student student);
}

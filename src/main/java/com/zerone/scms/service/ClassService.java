package com.zerone.scms.service;

import com.zerone.scms.model.ClassAndGrade;
import com.zerone.scms.model.Teacher;

import java.util.List;

public interface ClassService {
    boolean saveClass(ClassAndGrade classAndGrade);

    ClassAndGrade getClassById(Long id);

    List<ClassAndGrade> getAllClasses();

    List<ClassAndGrade> selectClassesByClassName(String className);

    ClassAndGrade selectClassByClassNameOne(String className);

    boolean deleteClass(Long id);

    boolean validateClass(ClassAndGrade classAndGrade);
}

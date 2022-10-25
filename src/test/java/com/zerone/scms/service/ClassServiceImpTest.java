package com.zerone.scms.service;

import com.zerone.scms.model.ClassAndGrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClassServiceImpTest {
    @Autowired
    ClassService classService;

    @Test
    @Transactional
    @Rollback(true)
    void saveClass() {
        ClassAndGrade classAndGrade = new ClassAndGrade();
        classAndGrade.setClassNo("test0001");
        classAndGrade.setMajor("计算机科学与技术");
        classAndGrade.setGrade("2022");
        classAndGrade.setClassName("计科1班");
        assertTrue(classService.saveClass(classAndGrade));
        ClassAndGrade newClassAndGrade = classService.getClassById(classAndGrade.getId());
        assertNotNull(newClassAndGrade);
        assertEquals(classAndGrade, newClassAndGrade);
    }

    @Test
    @Transactional
    @Rollback(true)
    void getClassById() {
        ClassAndGrade classAndGrade = new ClassAndGrade();
        classAndGrade.setClassNo("test0001");
        classAndGrade.setMajor("计算机科学与技术");
        classAndGrade.setGrade("2022");
        classAndGrade.setClassName("计科1班");
        assertTrue(classService.saveClass(classAndGrade));
        ClassAndGrade newClassAndGrade = classService.getClassById(classAndGrade.getId());
        assertNotNull(newClassAndGrade);
        assertEquals(classAndGrade, newClassAndGrade);
    }

    @Test
    @Transactional
    @Rollback(true)
    void getAllClasses() {
        List<ClassAndGrade> oldClassAndGrade = classService.getAllClasses();
        List<ClassAndGrade> classAndGrades = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ClassAndGrade classAndGrade = new ClassAndGrade();
            if (i % 2 == 0) {
                classAndGrade.setMajor("计算机科学与技术");
                if (i < 5) {
                    classAndGrade.setGrade("2021");
                    classAndGrade.setClassName("计科" + (i / 2) + "班");
                    classAndGrade.setClassNo("test110" + (i / 2));
                } else {
                    classAndGrade.setGrade("2022");
                    classAndGrade.setClassName("计科" + (i / 2) + "班");
                    classAndGrade.setClassNo("test120" + (i / 2));
                }
            } else {
                classAndGrade.setMajor("网络工程");
                if (i < 5) {
                    classAndGrade.setGrade("2021");
                    classAndGrade.setClassName("网工" + (i / 2) + "班");
                    classAndGrade.setClassNo("test210" + (i / 2));
                } else {
                    classAndGrade.setGrade("2022");
                    classAndGrade.setClassName("网工" + (i / 2) + "班");
                    classAndGrade.setClassNo("test220" + (i / 2));
                }
            }
            classService.saveClass(classAndGrade);
            classAndGrades.add(classAndGrade);
        }


        List<ClassAndGrade> allClassAndGrade = classService.getAllClasses();
        int count = 0;
        for (ClassAndGrade classAndGrade : classAndGrades) {
            for (int i = oldClassAndGrade.size(); i < allClassAndGrade.size(); i++) {
                if (classAndGrade.getId().longValue() == allClassAndGrade.get(i).getId().longValue()) {
                    assertEquals(classAndGrade, allClassAndGrade.get(i));
                    count++;
                }
            }
        }
        assertEquals(10, count);
    }

    @Test
    @Transactional
    @Rollback(true)
    void selectClassesByClassName() {
        List<ClassAndGrade> oldClassAndGrade = classService.selectClassesByClassName("计科");
        List<ClassAndGrade> oldClassAndGrade2 = classService.selectClassesByClassName("2");
        List<ClassAndGrade> classAndGrades = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ClassAndGrade classAndGrade = new ClassAndGrade();
            if (i % 2 == 0) {
                classAndGrade.setMajor("计算机科学与技术");
                if (i < 5) {
                    classAndGrade.setGrade("2021");
                    classAndGrade.setClassName("计科" + (i / 2) + "班");
                    classAndGrade.setClassNo("test110" + (i / 2));
                } else {
                    classAndGrade.setGrade("2022");
                    classAndGrade.setClassName("计科" + (i / 2) + "班");
                    classAndGrade.setClassNo("test120" + (i / 2));
                }
            } else {
                classAndGrade.setMajor("网络工程");
                if (i < 5) {
                    classAndGrade.setGrade("2021");
                    classAndGrade.setClassName("网工" + (i / 2) + "班");
                    classAndGrade.setClassNo("test210" + (i / 2));
                } else {
                    classAndGrade.setGrade("2022");
                    classAndGrade.setClassName("网工" + (i / 2) + "班");
                    classAndGrade.setClassNo("test220" + (i / 2));
                }
            }
            classService.saveClass(classAndGrade);
            classAndGrades.add(classAndGrade);
        }


        List<ClassAndGrade> allClassAndGrade = classService.selectClassesByClassName("计科");
        System.out.println(allClassAndGrade.size());
        assertEquals(5, allClassAndGrade.size() - oldClassAndGrade.size());
        List<ClassAndGrade> allClassAndGrade2 = classService.selectClassesByClassName("2");
        assertEquals(2, allClassAndGrade2.size() - oldClassAndGrade2.size());
        int count1 = 0;
        int count2 = 0;
        for (ClassAndGrade classAndGrade : classAndGrades) {
            for (int i = oldClassAndGrade.size(); i < allClassAndGrade.size(); i++) {
                if (classAndGrade.getId().longValue() == allClassAndGrade.get(i).getId().longValue()) {
                    assertEquals(classAndGrade, allClassAndGrade.get(i));
                    count1++;
                }
            }
            for (int i = oldClassAndGrade2.size(); i < allClassAndGrade2.size(); i++) {
                if (classAndGrade.getId().longValue() == allClassAndGrade2.get(i).getId().longValue()) {
                    assertEquals(classAndGrade, allClassAndGrade2.get(i));
                    count2++;
                }
            }
        }
        assertEquals(5, count1);
        assertEquals(2, count2);
    }

    @Test
    @Transactional
    @Rollback(true)
    void deleteClass() {
        ClassAndGrade classAndGrade = new ClassAndGrade();
        classAndGrade.setClassNo("test0000");
        classAndGrade.setMajor("计算机科学与技术");
        classAndGrade.setGrade("2022");
        classAndGrade.setClassName("计算机科学与技术");
        assertTrue(classService.saveClass(classAndGrade));
        assertNotNull(classService.getClassById(classAndGrade.getId()));
        ClassAndGrade classAndGrade1 = classService.getClassById(classAndGrade.getId());
        assertNotNull(classAndGrade1);
        assertTrue(classService.deleteClass(classAndGrade.getId()));
        ClassAndGrade deleteClassAndGrade = classService.getClassById(classAndGrade.getId());
        assertNull(deleteClassAndGrade);
    }

    @Test
    @Transactional
    @Rollback(true)
    void validateClass() {
        ClassAndGrade classAndGrade = new ClassAndGrade();
//        classAndGrade.setId(1L);
        classAndGrade.setClassName("19计科本2班");
        classAndGrade.setGrade("19级");
        classAndGrade.setMajor("计算机科学与技术");
        assertTrue(classService.validateClass(classAndGrade));
    }
}
package com.zerone.scms.mapper;

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
class ClassMapperTest {
    @Autowired
    ClassMapper classMapper;

    @Test
    @Transactional
    @Rollback
    void insertClass() {
        ClassAndGrade classAndGrade = new ClassAndGrade();
        classAndGrade.setClassNo("test0001");
        classAndGrade.setMajor("计算机科学与技术");
        classAndGrade.setGrade("2022");
        classAndGrade.setClassName("计科1班");
        assertEquals(1, classMapper.insertClass(classAndGrade));
        ClassAndGrade newClassAndGrade = classMapper.selectClassById(classAndGrade.getId());
        assertNotNull(newClassAndGrade);
        assertEquals(classAndGrade, newClassAndGrade);
    }

    @Test
    @Transactional
    @Rollback
    void updateClass() {
        ClassAndGrade classAndGrade = new ClassAndGrade();
        classAndGrade.setClassNo("test0000");
        classAndGrade.setMajor("计算机科学与技术");
        classAndGrade.setGrade("2022");
        classAndGrade.setClassName("计算机科学与技术");
        classMapper.insertClass(classAndGrade);
        ClassAndGrade classAndGrade1 = classMapper.selectClassById(classAndGrade.getId());
        assertEquals(classAndGrade, classAndGrade1);
        classAndGrade.setClassNo("test0001");
        classAndGrade.setMajor("网工1班");
        classAndGrade.setGrade("2021");
        classAndGrade.setClassName("网络工程");
        classMapper.updateClass(classAndGrade1);
        ClassAndGrade classAndGrade2 = classMapper.selectClassById(classAndGrade.getId());
        assertEquals(classAndGrade1, classAndGrade2);
    }

    @Test
    @Transactional
    @Rollback
    void deleteClass() {
        ClassAndGrade classAndGrade = new ClassAndGrade();
        classAndGrade.setClassNo("test0000");
        classAndGrade.setMajor("计算机科学与技术");
        classAndGrade.setGrade("2022");
        classAndGrade.setClassName("计算机科学与技术");
        assertEquals(1, classMapper.insertClass(classAndGrade));
        assertNotNull(classMapper.selectClassById(classAndGrade.getId()));
        ClassAndGrade classAndGrade1 = classMapper.selectClassById(classAndGrade.getId());
        assertNotNull(classAndGrade1);
        assertEquals(1, classMapper.deleteClass(classAndGrade.getId()));
        ClassAndGrade deleteClassAndGrade = classMapper.selectClassById(classAndGrade.getId());
        assertNull(deleteClassAndGrade);
    }

    @Test
    @Transactional
    @Rollback
    void selectClassesByClassName() {
        List<ClassAndGrade> oldClassAndGrade = classMapper.selectClassesByClassName("计科");
        List<ClassAndGrade> oldClassAndGrade2 = classMapper.selectClassesByClassName("2");
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
            classMapper.insertClass(classAndGrade);
            classAndGrades.add(classAndGrade);
        }


        List<ClassAndGrade> allClassAndGrade = classMapper.selectClassesByClassName("计科");
        System.out.println(allClassAndGrade.size());
        assertEquals(5, allClassAndGrade.size() - oldClassAndGrade.size());
        List<ClassAndGrade> allClassAndGrade2 = classMapper.selectClassesByClassName("2");
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
    @Rollback
    void selectAllClasses() {
        List<ClassAndGrade> oldClassAndGrade = classMapper.selectAllClasses();
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
            classMapper.insertClass(classAndGrade);
            classAndGrades.add(classAndGrade);
        }


        List<ClassAndGrade> allClassAndGrade = classMapper.selectAllClasses();
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
    @Rollback
    void selectClassById() {
        ClassAndGrade classAndGrade = new ClassAndGrade();
        classAndGrade.setClassNo("test0001");
        classAndGrade.setMajor("计算机科学与技术");
        classAndGrade.setGrade("2022");
        classAndGrade.setClassName("计科1班");
        assertEquals(1, classMapper.insertClass(classAndGrade));
        ClassAndGrade newClassAndGrade = classMapper.selectClassById(classAndGrade.getId());
        assertNotNull(newClassAndGrade);
        assertEquals(classAndGrade, newClassAndGrade);
    }

    @Test
    @Transactional
    @Rollback
    void selectClassByClassNameOne() {
    }
}
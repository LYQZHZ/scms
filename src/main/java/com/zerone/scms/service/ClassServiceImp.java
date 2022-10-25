package com.zerone.scms.service;

import com.zerone.scms.mapper.ClassMapper;
import com.zerone.scms.model.ClassAndGrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("classService")
public class ClassServiceImp implements ClassService {
    Logger logger = LoggerFactory.getLogger(ClassServiceImp.class);
    @Autowired
    ClassMapper classMapper;

    @Override
    public boolean saveClass(ClassAndGrade classAndGrade) {
        Boolean result = true;
        Long id = classAndGrade.getId();
        if (null == id) {
            if (1 != classMapper.insertClass(classAndGrade)) {
                result = false;
                throw new RuntimeException("班级信息保存失败！");
            }
        } else {
            if (1 != classMapper.updateClass(classAndGrade)) {
                result = false;
                throw new RuntimeException("班级信息更新失败！");
            }
        }
        return result;
    }

    @Override
    public ClassAndGrade getClassById(Long id) {
        return classMapper.selectClassById(id);
    }

    @Override
    public List<ClassAndGrade> getAllClasses() {
        return classMapper.selectAllClasses();
    }

    @Override
    public List<ClassAndGrade> selectClassesByClassName(String className) {
        return classMapper.selectClassesByClassName(className);
    }

    @Override
    public ClassAndGrade selectClassByClassNameOne(String className) {
        return classMapper.selectClassByClassNameOne(className);
    }

    @Override
    public boolean deleteClass(Long id) {
        Boolean result = true;
        ClassAndGrade classAndGrade = classMapper.selectClassById(id);
        if (classAndGrade != null) {
            if (1 != classMapper.deleteClass(classAndGrade.getId())) {
                result = false;
                throw new RuntimeException("删除班级信息失败！");
            }
        } else {
            result = false;
            throw new RuntimeException("要删除的班级不存在！");
        }
        return result;
    }

    @Override
    public boolean validateClass(ClassAndGrade classAndGrade) {
        boolean result = true;

        if (classAndGrade.getClassName() == null || classAndGrade.getClassName().length() == 0) {
            result = false;
        }
        if (classAndGrade.getGrade() == null || classAndGrade.getGrade().length() == 0) {
            result = false;
        }
        if (classAndGrade.getMajor() == null || classAndGrade.getMajor().length() == 0) {
            result = false;
        }

        return result;
    }


}

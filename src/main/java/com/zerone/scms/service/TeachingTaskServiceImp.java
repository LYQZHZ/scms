package com.zerone.scms.service;

import com.zerone.scms.mapper.*;
import com.zerone.scms.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("teachingTaskService")
public class TeachingTaskServiceImp implements TeachingTaskService {
    @Autowired
    ClassMapper classMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    TeachingTaskMapper teachingTaskMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    ScoreMapper scoreMapper;


    @Override
    public TeachingTask queryTeachingTaskByTtName(String ttname) {
        return teachingTaskMapper.selectTeachingTaskByTtName(ttname);
    }

    @Override
    public List<TeachingTask> getAllTeachingTasks() {
        return teachingTaskMapper.selectAllTeachingTasks();
    }

    @Override
    public List<TeachingTask> queryTeachingTasksByTtName(String ttname) {
        return teachingTaskMapper.selectTeachingTasksByName(ttname);
    }

    @Override
    public Boolean saveTeachingTask(TeachingTask teachingTask) {
        Boolean result = true;
        Long id = teachingTask.getId();
        System.out.println("------");
        System.out.println(id);
        System.out.println("------");
        if (id == null) {
            if (teachingTaskMapper.insertTeachingTask(teachingTask) == 1) {
                System.out.println("111111");
                List<Student> students = studentMapper.selectStudentsByClassGradeId(teachingTask.getClassAndGrade().getId());
                System.out.println(students.size());
                Course course = teachingTask.getCourse();
                List<Score> scores = new ArrayList<>();
                for (int i = 0; i < students.size(); i++) {
                    Student student = students.get(i);
                    Score score = new Score();
                    score.setStudent(student);
                    score.setCourse(course);
                    score.setAttendanceValue(0.0);
                    score.setHomeworkValue(0.0);
                    score.setExamValue(0.0);
                    score.setQuizValue(0.0);
                    scores.add(score);
                }
                scoreMapper.insertScores(scores);
            } else {
                result = false;
                throw new TransientDataAccessResourceException("教学任务保存失败!");
            }
        } else {
            if (teachingTaskMapper.updateTeachingTask(teachingTask) != 1) {
                result = false;
                throw new TransientDataAccessResourceException("教学任务保存失败!");
            }
        }
        return result;
    }

    @Override
    public TeachingTask getTeachingTaskById(Long id) {
        return teachingTaskMapper.selectTeachingTaskById(id);
    }



   /* @Override
    public TeachingTask selectTeachingTaskByTtNameOne(String ttname) {
        return teachingTaskMapper.;
    }*/

    @Override
    public boolean deleteTeachingTask(Long id) {
        Boolean result = true;
        TeachingTask teachingTask = teachingTaskMapper.selectTeachingTaskById(id);
        if (teachingTask != null) {
            if (1 != teachingTaskMapper.deleteTeachingTask(teachingTask.getId())) {
                result = false;
                throw new RuntimeException("删除教学任务信息失败！");
            }
        } else {
            result = false;
            throw new RuntimeException("要删除的班级不存在！");
        }
        return result;
    }

    @Override
    public boolean validateTeachingTask(TeachingTask teachingTask) {
        boolean result = true;

        if (teachingTask.getTtname() == null || teachingTask.getTtname().length() == 0) {
            result = false;
        }
//        if (teachingTask.getSchoolYear() == null || teachingTask.getSchoolYear().length() == 0) {
//            result = false;
//        }
//        if (teachingTask.getSchoolTerm() == 0) {
//            result = false;
//        }

        return result;
    }

}

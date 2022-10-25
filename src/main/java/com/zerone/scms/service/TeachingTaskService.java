package com.zerone.scms.service;

import com.zerone.scms.model.*;

import java.util.List;

public interface TeachingTaskService {
    TeachingTask queryTeachingTaskByTtName(String ttname);

    List<TeachingTask> getAllTeachingTasks();

    List<TeachingTask> queryTeachingTasksByTtName(String ttname);

    Boolean saveTeachingTask(TeachingTask teachingTask);

    TeachingTask getTeachingTaskById(Long id);
    
    boolean deleteTeachingTask(Long id);

    boolean validateTeachingTask(TeachingTask teachingTask);
}

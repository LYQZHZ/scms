package com.zerone.scms.service;

import com.zerone.scms.model.Score;

import java.util.List;

/**
 * @Author 杨毅龙
 * @Date
 * @注释
 */
public interface ScoreService {
    Boolean saveScore(Score score);
    Boolean saveScores(List<Score> scores);
    Boolean updateScore(Score score);
    Boolean deleteScore(Long id);
    Boolean clearScore(Long id);
    Score queryScoreById(Long id);
    List<Score> queryScoresByStudentId(Long studentId);
    List<Score> queryScoresByCourseId(Long courseId);
    List<Score> queryAllScores();
    Score queryScoresByCourseAndStudentId(Long CourseId,Long StudentId);
}

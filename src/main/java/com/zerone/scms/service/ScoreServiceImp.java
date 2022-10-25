package com.zerone.scms.service;

import com.zerone.scms.mapper.ScoreMapper;
import com.zerone.scms.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 杨毅龙
 * @Date
 * @注释
 */
@Service("scoreService")
public class ScoreServiceImp implements ScoreService{
    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public Boolean saveScore(Score score) {
        return scoreMapper.insertScore(score) == 1;
    }

    @Override
    public Boolean saveScores(List<Score> scores) {
        return scoreMapper.insertScores(scores) == scores.size();
    }

    @Override
    public Boolean updateScore(Score score) {
        return scoreMapper.updateScore(score) == 1;
    }

    @Override
    public Boolean deleteScore(Long id) {
        return scoreMapper.deleteScore(id) == 1;
    }

    @Override
    public Boolean clearScore(Long id) {
        Score score = scoreMapper.selectScoreById(id);
        score.setAttendanceValue(0.0);
        score.setExamValue(0.0);
        score.setHomeworkValue(0.0);
        score.setQuizValue(0.0);
        return scoreMapper.updateScore(score) == 1;
    }

    @Override
    public Score queryScoreById(Long id) {
        return scoreMapper.selectScoreById(id);
    }

    @Override
    public List<Score> queryScoresByStudentId(Long studentId) {
        return scoreMapper.selectScoreByStudentId(studentId);
    }

    @Override
    public List<Score> queryScoresByCourseId(Long courseId) {
        return scoreMapper.selectScoreByCourseId(courseId);
    }

    @Override
    public List<Score> queryAllScores() {
        return scoreMapper.selectAllScores();
    }

    @Override
    public Score queryScoresByCourseAndStudentId(Long CourseId, Long StudentId) {
        return scoreMapper.selectScoreByStudentIdAndCourseId(CourseId,StudentId);
    }
}

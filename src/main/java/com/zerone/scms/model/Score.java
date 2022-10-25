package com.zerone.scms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class Score implements Serializable {
    private Long id;
    private Student student;
    private Course course;
    private Double attendanceValue;//参与成绩
    private Double homeworkValue;//作业成绩
    private Double quizValue;//小测验成绩
    private Double examValue;//期末考试成绩
}

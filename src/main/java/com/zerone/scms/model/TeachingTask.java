package com.zerone.scms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class TeachingTask implements Serializable {
    private Long id;
    private String ttname;
    private ClassAndGrade classAndGrade;
    private Course course;
    private Teacher teacher;
    private Date beginDate;
    private Date endDate;
}

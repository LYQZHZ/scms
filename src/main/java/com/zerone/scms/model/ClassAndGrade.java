package com.zerone.scms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class ClassAndGrade implements Serializable {
    private Long id;
    private String classNo;
    private String className;
    private String grade;
    private String major;
}

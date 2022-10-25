package com.zerone.scms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class Student extends User implements Serializable {
    private Long id;
    private String sno;
    private String realName;
    private String gender;
    private Integer age;
    private String contactNumber;
    private Long classGradesId;
    private Long userId;
    private ClassAndGrade classAndGrade;
}

package com.zerone.scms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class Course implements Serializable {
    private Long id;
    private String cno;
    private String cname;
    private Integer period;
    private Double credit;
}

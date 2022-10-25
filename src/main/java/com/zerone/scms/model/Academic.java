package com.zerone.scms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class Academic extends User implements Serializable {
    private Long id;
    private String jobNo;
    private String realName;
    private String gender;
    private Integer age;
    private String contactNumber;
}

package com.zerone.scms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class Role implements Serializable {
    private Long id;
    private String roleName;
    private String roleDescription;
    private List<Authority> authorities;


}

package com.zerone.scms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(value = {"handler"})
public class User implements Serializable {
    private Long userId;
    private String username;
    private String password;
    private Boolean enabled;
    private Role role;
}

package com.ngj.register.model;

import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/20.
 */
@Data
public class Register {
    private String companyName;
    private String domain;
    private String release;
    private Long deadLine;
    private String username;
    private String password;
    private String mobile;
    private String name;
    private Integer status = 0;
}

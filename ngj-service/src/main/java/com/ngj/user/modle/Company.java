package com.ngj.user.modle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.ngj.base.FormData;
import lombok.Data;

import java.util.Date;

/**
 * Created by guanxinquan on 16/3/4.
 */
@Data
public class Company extends FormData{

    private Long id;

    private String name;

    private String domain;

    private Long ctime = System.currentTimeMillis();

    @JsonIgnore
    private Long utime = System.currentTimeMillis();

    private Integer maxUser;

    private Long deadLine;

    private Integer status = 1;


}

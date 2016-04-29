package com.ngj.templet.model;

import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/24.
 */
@Data
public class Templet {
    private static final Integer PERSONAL=0;
    private static final Integer SHARE=1;
    private static final Integer PROFESSIONAL=2;
    private Long id;
    private String name;
    private String content;
    private Long cTime;
    private Long uTime;
    private Long userId;
    private Integer type;
    private Integer status;
}


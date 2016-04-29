package com.ngj.signForm.model;

import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/25.
 */
@Data
public class SignForm {
    private static final Integer NOCHECK=0;
    private static final Integer CHECKING=1;
    private static final Integer REJECT=2;
    private static final Integer PASS=3;
    private Long id;
    private String contractname;
    private Long part_a;
    private Long part_b;
    private String part_a_opinion;
    private String part_b_opinion;
    private Long cTime;
    private Long uTime;
    private Integer situation;
    private Integer status;
}

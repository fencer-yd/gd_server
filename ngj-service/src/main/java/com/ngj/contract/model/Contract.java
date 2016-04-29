package com.ngj.contract.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * Created by pangyueqiang on 16/4/22.
 */
@Data
public class Contract {
    private static final Integer NO_CHECKED = 0; //未审核
    private static final Integer CHECKING = 1; //审核中
    private static final Integer PASS = 2;     //已通过
    private static final Integer  REJECT = 3;  //驳回
    private Long id;
    private String name;
    private Long part_a;
    private Long part_b;
    private Long effectiveTime;
    private Long expirationTime;
    private String content;
    private String  keyWords;
    private Integer situation = Contract.NO_CHECKED;
    private Long cTime;
    private Long uTime;
    private List<Object> keyWordList;
    private Integer status = 1;

}

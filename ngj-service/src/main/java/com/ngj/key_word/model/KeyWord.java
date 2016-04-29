package com.ngj.key_word.model;

import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/25.
 */
@Data
public class KeyWord {
    private Long id;
    private String name;
    private Long tenant;
    private Long cTime;
    private Long uTime;
    private Integer status;
}

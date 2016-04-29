package com.ngj.item.model;

import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/25.
 */
@Data
public class Item {
    private Long id;
    private String name;
    private Long tenant;
    private String content;
    private Long cTime;
    private Long uTime;
    private Integer status;

}

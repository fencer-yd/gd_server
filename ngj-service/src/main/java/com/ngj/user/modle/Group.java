package com.ngj.user.modle;

import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/22.
 */
@Data
public class Group {
    private Long id;
    private Long tenant;
    private String name;
    private Long cTime;
    private Long uTime;

    private Integer status;
}

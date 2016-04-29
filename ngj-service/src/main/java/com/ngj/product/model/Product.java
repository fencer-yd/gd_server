package com.ngj.product.model;

import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/25.
 */
@Data
public class Product {
    private Long id;
    private Long tenant;
    private String name;
    private String desc;
    private String leader;
    private Long cTime;
    private Long uTime;
    private Integer status;

}

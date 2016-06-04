package com.ngj.product.model;

import com.ngj.base.FormData;
import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/25.
 */
@Data
public class Product extends FormData{
    private Long id;
    private Long tenant;
    private String name;
    private String desc;
    private String leader;

}

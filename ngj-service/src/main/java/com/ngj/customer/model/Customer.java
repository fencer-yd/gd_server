package com.ngj.customer.model;

import com.ngj.base.FormData;
import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/22.
 */
@Data
public class Customer extends FormData {
    private Long tenant;
    private String name;
    private String address;
    private String contact;
    private Integer size;
    private String describle;
    private Long cTime;
    private Long uTime;
    private Integer status;
}

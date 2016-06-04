package com.ngj.rest.req;

import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by guanxinquan on 16/3/9.
 */
@Data
public class CompanyChangeReq {

    private Long id;
    private String name;
    private String address;
    private String contact;
    private Integer size;
    private String describle;


}

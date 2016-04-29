package com.ngj.rest.req;

import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by guanxinquan on 16/3/9.
 */
@Data
public class CompanyChangeReq {

    private Integer maxUser;

    private Long deadLine;

    private Integer status;

}

package com.ngj.rest.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by guanxinquan on 16/3/8.
 */
@Data
@ApiModel
public class CompanyCreateReq {

    @ApiModelProperty(required = true)
    private String name;

    @ApiModelProperty(required = true)
    private String domain;

    @ApiModelProperty(notes = "default 10")
    private Integer maxUser = 10;

    @ApiModelProperty(notes = "default 0 永不过期")
    private Long deadLine;

    private Integer status = 0;
}

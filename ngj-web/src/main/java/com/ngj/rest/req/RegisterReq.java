package com.ngj.rest.req;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/20.
 */
@Data
@ApiModel
public class RegisterReq {
    @ApiModelProperty(required = true)
    private String companyName;

    @ApiModelProperty(required = true)
    private String domain;

    @ApiModelProperty(notes = "standard，senior 下拉可选")
    private String release;

    @ApiModelProperty(notes = "default 0 永不过期")
    private Long deadLine;

    @ApiModelProperty(required = true)
    private String username;

    @ApiModelProperty(required = true)
    private String password;

    private String mobile;

    @ApiModelProperty(value = "名字",required = true)
    private String name;

    private Integer status = 0;
}

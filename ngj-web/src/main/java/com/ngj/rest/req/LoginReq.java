package com.ngj.rest.req;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by guanxinquan on 16/3/1.
 */
@Data
@ApiModel
public class LoginReq {

    @ApiModelProperty(required = true)
    private String username;

    @ApiModelProperty(required = true)
    private String domain;

    @ApiModelProperty(required = true)
    private String password;

}

package com.ngj.rest.req;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by guanxinquan on 16/3/7.
 */
@Data
@ApiModel
public class UserCreateReq {

    @ApiModelProperty(required = true)
    private String username;

    @ApiModelProperty(required = true)
    private String password;

    @ApiModelProperty(value = "角色",allowableValues = "ROLE_USER,ROLE_ADMIN",required = true)
    private String role;

    private String mobile;

    @ApiModelProperty(value = "名字",required = true)
    private String name;
}

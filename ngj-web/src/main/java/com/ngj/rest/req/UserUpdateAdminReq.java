package com.ngj.rest.req;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by guanxinquan on 16/3/7.
 */
@Data
@ApiModel
public class UserUpdateAdminReq {

    @ApiModelProperty(required = true)
    private Long id;

    private String username;

    private String password;

    @ApiModelProperty(value = "角色",allowableValues = "ROLE_USER,ROLE_ADMIN")
    private String role;

    private String mobile;

    private String name;

}

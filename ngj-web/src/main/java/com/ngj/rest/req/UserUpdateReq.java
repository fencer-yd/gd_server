package com.ngj.rest.req;

import lombok.Data;

/**
 * Created by guanxinquan on 16/3/7.
 */
@Data
public class UserUpdateReq {

    private String username;

    private String password;

    private String mobile;

    private String name;
}

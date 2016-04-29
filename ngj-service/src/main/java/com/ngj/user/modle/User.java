package com.ngj.user.modle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Created by guanxinquan on 16/2/29.
 */
@Data
public class User {

    private Long id;

    private String username;

    private String password;

    @JsonIgnore
    private Long tenant;

    private String role;

    @JsonIgnore
    private Long ctime = System.currentTimeMillis();

    private Long utime = System.currentTimeMillis();

    private String mobile;

    private String name;
    private Integer status = 1;
    @JsonIgnore
    private Long refresh = System.currentTimeMillis();

    public static final boolean checkRoles(String role){
        if (role == null){
            return true;
        }

        if("ROLE_USER".equals(role)){
            return true;
        }else if("ROLE_ADMIN".equals(role)){
            return true;
        }
        return false;
    }

}

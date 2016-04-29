package com.ngj.user.modle;

import lombok.Data;

import java.util.List;

/**
 * Created by guanxinquan on 16/3/1.
 */
@Data
public class UserPassport {

    private Long id;

    private String token;

    private Long tenant;

    private String role;

    private String username;

    private Long refresh;

    private String name;

    public UserPassport(Long id, String token, Long tenant, String role, String username,String name,Long refresh) {
        this.id = id;
        this.token = token;
        this.tenant = tenant;
        this.role = role;
        this.username = username;
        this.refresh = refresh;
        this.name = name;
    }

    public UserPassport() {
    }

}

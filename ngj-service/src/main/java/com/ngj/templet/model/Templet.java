package com.ngj.templet.model;

import com.ngj.base.FormData;
import lombok.Data;

/**
 * Created by pangyueqiang on 16/4/24.
 */

public class Templet extends FormData{
    private static final Integer PERSONAL=0;
    private static final Integer SHARE=1;
    private static final Integer PROFESSIONAL=2;
    private Long id;
    private String name;
    private String content;
    private Long userId;
    private String userName;
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}


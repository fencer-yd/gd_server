package com.ngj.rest.resp;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by guanxinquan on 16/3/1.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonsResp<T> {

    private T value;

    private Integer code = 0;

    private String msg;

    public CommonsResp(T value) {
        this.value = value;
    }

    public CommonsResp(T value, Integer code) {
        this.value = value;
        this.code = code;
    }

    public CommonsResp(T value, Integer code, String msg) {
        this.value = value;
        this.code = code;
        this.msg = msg;
    }

    public CommonsResp() {
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

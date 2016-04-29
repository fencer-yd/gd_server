package com.ngj.exception;

/**
 * Created by guanxinquan on 16/3/14.
 * 提示异常
 * 属于提示异常的exception 将会被转换成CommonseResp,对应的code为10,对应的msg为Exception的message
 */
public class PromptException extends Exception{

    private static final Integer baseCode = 10;

    public PromptException() {
    }
    public PromptException(String message) {
        super(message);
    }

    public PromptException(String message, Throwable cause) {
        super(message, cause);
    }

    public PromptException(Throwable cause) {
        super(cause);
    }

    public PromptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Integer getCode(){
        return baseCode;
    }
}

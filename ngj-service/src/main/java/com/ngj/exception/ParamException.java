package com.ngj.exception;

/**
 * Created by pangyueqiang on 16/3/14.
 * 参数验证异常错误
 */
public class ParamException extends PromptException {

    private static final Integer code = 11;

    public ParamException() {
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public Integer getCode() {
        return code;
    }
}

package com.zypcy.framework.fast.common.error;

import com.zypcy.framework.fast.common.response.ResultCodeEnum;
import com.zypcy.framework.fast.common.response.ResultEnum;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected String code = ResultCodeEnum.FAIL;
    protected String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
    }

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(ResultEnum result) {
        this.code = result.getResultCode();
        this.message = result.getResultCode();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

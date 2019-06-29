package com.zypcy.framework.fast.common.error;

import com.zypcy.framework.fast.common.response.ResultCodeEnum;
import com.zypcy.framework.fast.common.response.ResultEnum;
import lombok.Getter;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @Getter
    protected String code = ResultCodeEnum.FAIL;
    @Getter
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
}

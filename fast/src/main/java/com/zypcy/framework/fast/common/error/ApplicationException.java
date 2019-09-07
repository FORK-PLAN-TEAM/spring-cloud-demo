package com.zypcy.framework.fast.common.error;

import com.zypcy.framework.fast.common.response.ResultEnum;

/**
 * 应用异常
 */
public class ApplicationException extends RuntimeException  {

    private static final long serialVersionUID = 1L;

    private String code;
    private String message;

    public ApplicationException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ApplicationException(ResultEnum result) {
        super(result.getResultMessage());
        this.code = result.getResultCode();
        this.message = result.getResultMessage();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

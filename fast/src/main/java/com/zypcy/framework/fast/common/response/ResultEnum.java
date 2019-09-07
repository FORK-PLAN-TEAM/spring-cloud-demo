package com.zypcy.framework.fast.common.response;


public enum ResultEnum {

    /**
     * 基本
     */
    SUCCESS(ResultCodeEnum.SUCCESS, "操作成功"),
    FAIL(ResultCodeEnum.FAIL, "操作失败"),
    EXCEPTION(ResultCodeEnum.EXCEPTION, "服务异常"),

    /**
     * 数据相关
     */
    DATA_NOTFOUND(ResultCodeEnum.DATA_NOTFOUND, "数据不存在"),

    /**
     * 账户相关
     */
    ACCOUNT_NOTFOUND(ResultCodeEnum.ACCOUNT_NOTFOUND, "身份令牌不存在"),
    ACCOUNT_INCORRECT(ResultCodeEnum.ACCOUNT_INCORRECT, "身份令牌错误"),
    //ACCOUNT_PERMISSION_DENIED(ResultCodeEnum.ERR_ACCOUNT_PERMISSION_DENIED, "没有权限访问"),
    ACCOUNT_TIMEOUT(ResultCodeEnum.ACCOUNT_TIMEOUT, "身份令牌超时");


    private String resultCode;

    private String resultMessage;

    ResultEnum(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }
}

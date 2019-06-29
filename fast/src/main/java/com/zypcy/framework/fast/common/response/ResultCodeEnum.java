package com.zypcy.framework.fast.common.response;

/**
 * 返回码枚举
 * zhuyu
 */
public interface ResultCodeEnum {

    //成功
    String SUCCESS = "0000";
    //失败
    String FAIL = "0001";
    //服务异常  超时、熔断、降级
    String EXCEPTION = "0002";


    //身份令牌不存在
    String ACCOUNT_NOTFOUND = "1003";
    //令牌错误
    String ACCOUNT_INCORRECT = "1004";
    //身份令牌超时
    String ACCOUNT_TIMEOUT = "1005";
    //没有权限访问
    String ACCOUNT_PERMISSION_DENIED = "1006";

    //数据没找到
    String DATA_NOTFOUND = "1007";
}

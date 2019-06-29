package com.zypcy.framework.fast.common.response;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 获取返回结果，统一包装为ResponseModel给前端
 */
public class ResponseBodyWrapHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public ResponseBodyWrapHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        //本身就是 ResponseModel 直接返回
        if (returnValue instanceof ResponseModel) {
            delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
            return;
        }

        ResponseModel response = ResponseModel.successInstance();
        response.setResultObj(returnValue);
        delegate.handleReturnValue(response, returnType, mavContainer, webRequest);
    }
}

package com.zypcy.framework.fast.common.config;

import org.springframework.web.context.request.RequestAttributes;

/**
 * @Author zhuyu
 * @Time 2020-06-12 09:54
 * @Description service层获取request时，返回空，防止报错
 */
public class NonWebRequestAttributes implements RequestAttributes {

    @Override
    public Object getAttribute(String name, int scope) {
        return null;
    }

    @Override
    public void setAttribute(String name, Object value, int scope) {

    }

    @Override
    public void removeAttribute(String name, int scope) {

    }

    @Override
    public String[] getAttributeNames(int scope) {
        return new String[0];
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback, int scope) {

    }

    @Override
    public Object resolveReference(String key) {
        return null;
    }

    @Override
    public String getSessionId() {
        return null;
    }

    @Override
    public Object getSessionMutex() {
        return null;
    }
}
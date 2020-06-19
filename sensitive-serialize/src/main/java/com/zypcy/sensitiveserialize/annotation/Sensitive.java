package com.zypcy.sensitiveserialize.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author zhuyu
 * @Time 2020-06-19 10:48
 * @Description 数据脱敏注解 描述
 */
@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.FIELD,ElementType.METHOD})
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerialize.class)
public @interface Sensitive {
    /**
     * 要对那种类型的数据脱敏
     * @return
     */
    public SensitiveType value();
}

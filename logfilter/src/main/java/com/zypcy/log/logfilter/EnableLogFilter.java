package com.zypcy.log.logfilter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(LogFilterAutoConfiguration.class) //引入自动配置加载类
public @interface EnableLogFilter {
}

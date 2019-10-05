package com.zypcy.log.logfilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

/**
 * 把LogFilter包装成Spring Bean
 */
public class LogFilterRegistrationBean extends FilterRegistrationBean<LogFilter> {

    public LogFilterRegistrationBean(){
        super();
        this.setFilter(new LogFilter());//添加LogFilter过滤器
        this.addUrlPatterns("/*");
        this.setOrder(10);
        this.setName("logFilter");
    }

}

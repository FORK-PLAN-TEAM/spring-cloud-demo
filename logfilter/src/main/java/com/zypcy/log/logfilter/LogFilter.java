package com.zypcy.log.logfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器处理操作
 */
public class LogFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("logFilter init ...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //从Reqest中获取访问地址，并打印到控制台
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        logger.info("logFilter uri : " + request.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.info("logFilter destroy ...");
    }
}

package com.zypcy.framework.fast.common.config;


import com.zypcy.framework.fast.common.util.LogUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

//系统框架切面
//AOP切面的优先级：需要@Order(i)注解来标识切面的优先级。i的值越小，优先级越高
@Order(2)
@Aspect
@Component
public class FastAspect {

    //系统框架相关控制器
    @Pointcut("execution(public * com.zypcy.framework.fast.*.controller.*.*(..))")
    public void log() {
    }

    //统计请求的处理时间
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录请求的内容
        //System.out.println("Aspect_URL:"+request.getRequestURL().toString());
        //System.out.println("Aspect_Method:"+request.getMethod());
        //System.out.println("IP:"+request.getRemoteAddr());
        //System.out.println("Aspect_Controller_Method:"+joinPoint.getSignature().getDeclaringTypeName()+"--"+joinPoint.getSignature().getName());
        LogUtil.info("请求URL:" + request.getRequestURL().toString());
        Enumeration enu = request.getParameterNames();
        String params = "";
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            params += paraName + ":" + request.getParameter(paraName) + ",";
        }
        LogUtil.info("请求参数:" + params);
    }

    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(Object ret) {
        //处理完请求后，返回内容
        //System.out.println("方法返回值:"+ JSON.toJSONString(ret));
        LogUtil.info("方法执行时间:" + (System.currentTimeMillis() - startTime.get()) + "ms");
    }
}

package com.zypcy.springcloud.orderserviceprometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 一 AOP的基本概念
 * (1)Aspect(切面):通常是一个类，里面可以定义切入点和通知
 * (2)JointPoint(连接点):程序执行过程中明确的点，一般是方法的调用
 * (3)Advice(通知):AOP在特定的切入点上执行的增强处理，有before,after,afterReturning,afterThrowing,around
 * (4)Pointcut(切入点):就是带有通知的连接点，在程序中主要体现为书写切入点表达式
 * (5)AOP代理：AOP框架创建的对象，代理就是目标对象的加强。Spring中的AOP代理可以使JDK动态代理，也可以是CGLIB代理，前者基于接口，后者基于子类
 * <p>
 * 2.通知类型介绍
 * (1)Before:在目标方法被调用之前做增强处理,@Before只需要指定切入点表达式即可
 * (2)AfterReturning:在目标方法正常完成后做增强,@AfterReturning除了指定切入点表达式后，还可以指定一个返回值形参名returning,代表目标方法的返回值
 * (3)AfterThrowing:主要用来处理程序中未处理的异常,@AfterThrowing除了指定切入点表达式后，还可以指定一个throwing的返回值形参名,可以通过该形参名来访问目标方法中所抛出的异常对象
 * (4)After:在目标方法完成之后做增强，无论目标方法时候成功完成。@After可以指定一个切入点表达式
 * (5)Around:环绕通知,在目标方法完成前后做增强处理,环绕通知是最重要的通知类型,像事务,日志等都是环绕通知,注意编程中核心是一个ProceedingJoinPoint
 */
@Component
@Aspect
public class AspectAop {

    @Pointcut("execution(public * com.zypcy.springcloud.orderserviceprometheus.controller.*.*(..))")
    public void pointCut() {
    }

    //统计请求的处理时间
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Autowired
    MeterRegistry registry;

    private Counter counter;

    @PostConstruct
    private void init() {
        counter = registry.counter("requests_total");
    }


    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        //接收到请求，记录请求内容
        //ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //HttpServletRequest request = attributes.getRequest();

        counter.increment(); //记录系统总请求数
    }

    @AfterReturning(returning = "returnVal", pointcut = "pointCut()")
    public void doAfterReturning(Object returnVal) {
        //处理完请求后，返回内容
        System.out.println("方法执行时间:" + (System.currentTimeMillis() - startTime.get()));

    }

}

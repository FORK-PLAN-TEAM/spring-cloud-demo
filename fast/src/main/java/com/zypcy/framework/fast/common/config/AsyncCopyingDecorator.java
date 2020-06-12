package com.zypcy.framework.fast.common.config;

import org.springframework.core.task.TaskDecorator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 异步任务时，复制上下文信息到异步线程中，供业务获取用户信息
 *
 * 如果是web请求则会有RequestAttributes，并把用户上下文信息设置进去，供异步任务获取
 * 如果是定时任务，则不会有RequestAttributes，返回NonWebRequestAttributes，获取用户信息时需要判断 userInfo 对象是否为 null
 * 获取示例： RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
 *          UserInfo user =  (UserInfo)requestAttributes.getAttribute("userInfo" , RequestAttributes.SCOPE_REQUEST);
 *          if(user != null){
 *              something();
 *          }
 * @Author zhuyu
 * @Time 2020-06-05 17:57
 * @Description 复制上下文信息到异步线程中
 */
public class AsyncCopyingDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        RequestAttributes context = getRequestAttributesSafely();
        return () -> {
            try {
                RequestContextHolder.setRequestAttributes(context);
                runnable.run();
            }finally {
                RequestContextHolder.resetRequestAttributes();
            }
        };
    }

    /**
     * 获取RequestAttributes对象，如果未取到，则返回空引用，不让业务报错
     * @return
     */
    public RequestAttributes getRequestAttributesSafely(){
        RequestAttributes requestAttributes = null;
        try{
            requestAttributes = RequestContextHolder.currentRequestAttributes();
            //如果是web请求则会有request，并把用户上下文信息设置进去，供异步任务获取
            //异步任务中，需要判断获取的 userInfo 对象是否为 null
            if(requestAttributes != null){
                //HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
                requestAttributes.setAttribute( "userInfo" , ContextHolder.getUserInfo() , RequestAttributes.SCOPE_REQUEST);
            }
        }catch (IllegalStateException e){
            requestAttributes = new NonWebRequestAttributes();
        }
        return requestAttributes;
    }

}

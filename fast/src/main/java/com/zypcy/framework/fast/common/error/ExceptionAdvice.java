package com.zypcy.framework.fast.common.error;


import com.zypcy.framework.fast.common.response.ResponseModel;
import com.zypcy.framework.fast.common.response.ResultCodeEnum;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局统一异常处理
 * 捕获异常，产生异常时，统一返回错误信息
 * 请不要在Controller层与Service层自行try catch捕获异常，否则全局异常处理器无法捕获
 * 如需使用异常，请使用 throw new BusinessException("null异常....."); 抛出，全局异常就能捕获到
 */
@ControllerAdvice
public class ExceptionAdvice {

    //此方法把请求转发给SpringBoot的错误处理，它能根据请求头是否有text/html，来返回页面还是json数据
    //@ExceptionHandler(value = Exception.class)
    //public String handle1(Exception e , HttpServletRequest request) {
    //    request.setAttribute("javax.servlet.error.status_code" , 400);
    //    return "forward:/error";
    //}

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseModel handle(Exception e) {
        if (e instanceof NullPointerException) {
            return ResponseModel.failInstance();
        } else if (e instanceof ApplicationException) {
            ApplicationException ae = (ApplicationException) e;
            return ResponseModel.Builder.builder().code(ae.getCode()).message(ae.getMessage()).build();
        }else if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            return ResponseModel.Builder.builder().code(be.getCode()).message(be.getMessage()).build();
        }else if(e instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException me = (MethodArgumentNotValidException)e;
            String msg = me.getBindingResult().getFieldError().getDefaultMessage();
            return ResponseModel.Builder.builder()
                    .code(ResultCodeEnum.FAIL)
                    .message(msg)
                    .build();
        }
        else {
            //默认异常处理
            return ResponseModel.Builder.builder()
                    .code(ResultCodeEnum.FAIL)
                    .message(e.getMessage())
                    .build();
        }
    }
}

package com.zypcy.framework.fast.common.util;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.log.level.Level;

/**
 * 日志帮助类
 */
public class LogUtil {

    private static final Log log = LogFactory.get();


    /**
     * debug日志
     * @param str
     * @param var2
     */
    public static void debug(String str ,  Object... var2){
        log.log(Level.DEBUG , str , var2);
    }

    /**
     * info日志
     * @param str
     */
    public static void info(String str ,  Object... var2){
        log.log(Level.INFO , str , var2);
    }

    /**
     * warn日志
     * @param str
     */
    public static void warn(String str ,  Object... var2){
        log.log(Level.WARN , str , var2);
    }

    /**
     * warn日志
     * @param str
     */
    public static void error(String str ,  Object... var2){
        log.log(Level.ERROR , str , var2);
    }

    /**
     * error日志
     * @param str
     */
    public static void error(String str , Throwable var2){
        log.log(Level.ERROR , var2 , str);
    }

    /**
     * trace日志
     * @param str
     */
    public static void trace(String str,  Object... var2){
        log.log(Level.TRACE , str , var2);
    }
}

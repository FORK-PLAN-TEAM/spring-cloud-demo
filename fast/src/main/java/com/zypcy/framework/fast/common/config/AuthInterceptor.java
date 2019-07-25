package com.zypcy.framework.fast.common.config;

import com.zypcy.framework.fast.sys.dto.ZySysLoginInfo;
import com.zypcy.framework.fast.sys.entity.ZySysUser;
import com.zypcy.framework.fast.sys.factory.LoginFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 身份认证拦截器，判断是否有权限
 */
public class AuthInterceptor implements HandlerInterceptor {

    //获取配置文件中的token过期时间, 单位分钟，默认120
    private long tokenExpireTime;

    public AuthInterceptor(long expireTime) {
        tokenExpireTime = expireTime;
    }

    /**
     * 判断是否有权限请求
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String token = getTokenByName(request, "token");
        //LogUtil.info("token:" + token);
        if (!StringUtils.isEmpty(token)) {
            ZySysLoginInfo userInfo = LoginFactory.getUserLoginInfo(token);
            if(userInfo != null && userInfo.getSysUser() != null){
                ZySysUser sysUser = userInfo.getSysUser();
                //判断是否过期
                if(!isExpire(sysUser.getLoginTime())){
                    flag = true;
                    ContextHolder.setUserInfo(userInfo);
                    return true;
                }
            }
        }
        if(!flag){
            //没有携带token,或token中没有数据的请求,拦截掉
            //throw new ApplicationException(ResultEnum.ACCOUNT_NOTFOUND);
            response.sendRedirect("/");
            return false;
        }
        return true;
    }

    /**
     * 根据名称获取Token
     * @param request
     * @param name
     * @return
     */
    public String getTokenByName(HttpServletRequest request, String name) {
        String result = "";
        Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    result = cookie.getValue();
                }
            }
        }
        return result;
    }


    /**
     * 判断token是否过期
     * @param time token生成时间
     * @return
     */
    public boolean isExpire(long time) {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - time) > (tokenExpireTime * 60 * 1000)) {
            //已过期
            return true;
        }
        return false;
    }
}

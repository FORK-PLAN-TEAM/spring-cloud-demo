package com.zypcy.framework.fast.common.config;

import com.zypcy.framework.fast.common.util.LoginExpiresUtil;
import com.zypcy.framework.fast.sys.cache.UserLoginCache;
import com.zypcy.framework.fast.sys.dto.ZySysLoginInfo;
import com.zypcy.framework.fast.sys.entity.ZySysUser;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

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
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String token = LoginExpiresUtil.getTokenByName(request, "token");
        //LogUtil.info("token:" + token);
        if (!StringUtils.isEmpty(token)) {
            ZySysLoginInfo userInfo = UserLoginCache.getUserLoginInfo(token);
            if (userInfo != null && userInfo.getSysUser() != null) {
                ZySysUser sysUser = userInfo.getSysUser();
                //判断是否过期
                if (!LoginExpiresUtil.isExpire(tokenExpireTime, sysUser.getLoginTime(), sysUser.getLoginPlatform())) {
                    flag = true;
                    ContextHolder.setUserInfo(userInfo);
                    return true;
                } else {
                    UserLoginCache.removeLoginInfo(token);
                }
            }
        }
        if (!flag) {
            //没有携带token,或token中没有数据的请求,拦截掉
            //throw new ApplicationException(ResultEnum.ACCOUNT_NOTFOUND);
            response.sendRedirect("/");
            return false;
        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //移除用户信息，防止内存泄露
        ContextHolder.remove();
    }
}

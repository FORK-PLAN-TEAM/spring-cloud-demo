package com.zypcy.multicache.service.inf;

import com.alicp.jetcache.anno.*;
import com.zypcy.multicache.pojo.entity.User;

import java.util.concurrent.TimeUnit;

/**
 * @Author 64590 作者
 * @Time 2020-06-24 10:28
 * @Description 描述
 */
public interface IndexService {

    void addUser(User user);

    @CacheRefresh(refresh = 30, stopRefreshAfterLastAccess = 600, timeUnit = TimeUnit.SECONDS)
    @CachePenetrationProtect
    @Cached(name = "userCache-", key = "#userId", expire = 60)
    User getUserById(long userId);

    @CacheUpdate(name = "userCache-", key = "#user.userId", value = "#user")
    void updateUser(User user);

    @CacheInvalidate(name = "userCache-", key = "#userId")
    void deleteUser(long userId);

}

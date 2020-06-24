package com.zypcy.multicache.service.impl;

import com.alibaba.fastjson.JSON;
import com.zypcy.multicache.pojo.entity.User;
import com.zypcy.multicache.service.inf.IndexService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author 64590 作者
 * @Time 2020-06-24 10:29
 * @Description 描述
 */
@Service
public class IndexServiceImpl implements IndexService {

    private static final String key = "user";
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public void addUser(User user) {
        redisTemplate.opsForHash().put(key , String.valueOf(user.getUserId()) , JSON.toJSONString(user));
    }

    @Override
    public User getUserById(long userId) {
        User user = null;
        Object result = redisTemplate.opsForHash().get(key , String.valueOf(userId));
        if(result != null){
            user = JSON.parseObject(result.toString() , User.class);
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        User dbUser = getUserById(user.getUserId());
        if(dbUser != null){
            if(!StringUtils.isEmpty(user.getName())){
                dbUser.setName(user.getName());
            }
            if(!StringUtils.isEmpty(user.getAddress())){
                dbUser.setAddress(user.getAddress());
            }
            if(user.getAge() > 0){
                dbUser.setAge(user.getAge());
            }
            addUser(dbUser);
        }
    }

    @Override
    public void deleteUser(long userId) {
        redisTemplate.opsForHash().delete(key , String.valueOf(userId));
    }
}

package com.zypcy.springcloud.dynamicroute.controller;

import com.zypcy.springcloud.dynamicroute.config.RedisConfig;
import com.zypcy.springcloud.dynamicroute.entity.DynamicVersion;
import com.zypcy.springcloud.dynamicroute.service.inf.IDynamicVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/version")
public class DynamicVersionController {

    @Autowired private IDynamicVersionService dynamicVersionService;
    @Autowired private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "/add" , method = RequestMethod.GET)
    public String add(ModelMap map){
        DynamicVersion version = new DynamicVersion();
        int result = dynamicVersionService.add(version);
        map.addAttribute("result" , result);
        return "addVersion";
    }

    //获取最后一次发布的版本号
    @RequestMapping(value = "/lastVersion" , method = RequestMethod.GET)
    @ResponseBody
    public Long getLastVersion(){
        Long versionId = 0L;
        String result = redisTemplate.opsForValue().get(RedisConfig.versionKey);
        if(!StringUtils.isEmpty(result)){
            System.out.println("返回 redis 中的版本信息......");
            versionId = Long.valueOf(result);
        }else{
            System.out.println("返回 mysql 中的版本信息......");
            versionId = dynamicVersionService.getLastVersion();
            redisTemplate.opsForValue().set(RedisConfig.versionKey , String.valueOf(versionId));
        }
        return versionId;
    }


    //打开发布版本列表页面
    public String listAll(ModelMap map){
        map.addAttribute("list", dynamicVersionService.listAll());
        return "versionlist";
    }
}

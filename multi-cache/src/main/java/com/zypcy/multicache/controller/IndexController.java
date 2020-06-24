package com.zypcy.multicache.controller;

import com.zypcy.multicache.pojo.entity.User;
import com.zypcy.multicache.service.inf.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 64590 作者
 * @Time 2020-06-24 10:23
 * @Description 描述
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    IndexService indexService;

    @PostMapping("/add")
    public String addUser(User user) {
        indexService.addUser(user);
        return "success";
    }

    @GetMapping("/get")
    public User getUserById(long userId) {
        return indexService.getUserById(userId);
    }

    @PostMapping("/update")
    public String  updateUser(User user) {
        indexService.updateUser(user);
        return "success";
    }

    @GetMapping("/delete")
    public String deleteUser(long userId) {
        indexService.deleteUser(userId);
        return "success";
    }
}

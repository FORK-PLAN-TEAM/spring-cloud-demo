package com.zypcy.file.minioservice.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zypcy.file.minioservice.service.MinioService;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private MinioService minioService;

    @RequestMapping("/")
    public String index(ModelMap map , @RequestParam(value = "pageIndex" , defaultValue = "1") int pageIndex, @RequestParam(value = "pageSize" , defaultValue = "10") int pageSize){
        List<JSONObject> data = new ArrayList<>();
        Iterable<Result<Item>> list = minioService.listObjects("test", null , null , 10);
        list.forEach( itemResult -> {
            try {
                Item item = itemResult.get();
                JSONObject d1 = JSONUtil.createObj();
                d1.set("objectName" , item.objectName());
                d1.set("size" , item.size());
                d1.set("isDir" , item.isDir());//是否目录
                data.add(d1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        map.addAttribute("list" , data);
        return "index";
    }

    @RequestMapping("/upload")
    public String upload(){
        return "upload";
    }

    @RequestMapping("/jsupload")
    public String jsupload(){
        return "jsupload";
    }

}

package com.zypcy.framework.fast;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.reflect.TypeToken;
import com.zypcy.framework.fast.common.response.PageModel;
import com.zypcy.framework.fast.common.util.LogUtil;
import com.zypcy.framework.fast.common.util.RedisUtil;
import com.zypcy.framework.fast.sys.cache.DictCache;
import com.zypcy.framework.fast.sys.constant.KeyConstant;
import com.zypcy.framework.fast.sys.entity.ZySysDict;
import com.zypcy.framework.fast.sys.entity.ZySysMenu;
import com.zypcy.framework.fast.sys.entity.ZySysRoleMenu;
import com.zypcy.framework.fast.sys.service.IZySysMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FastApplicationTests {

    @Test
    public void contextLoads() {
    }

    /*
    @Test
    public void md5() {
        String data = "123456" + "123456";
        //System.out.println(SecureUtil.md5(data));
        String newStr = SecureUtil.md5(data);
        //debug(newStr);
        LogUtil.debug("debug:" + newStr);
        LogUtil.info("info:" + newStr);
        LogUtil.warn("warn:" + newStr);
        LogUtil.error("error:" + newStr);
        LogUtil.trace("trace:" + newStr);
    }

    @Test
    public void testRedis() {

        //Strings
        //RedisUtil.Strings.set("name" , menu1);
        //LogUtil.info("Strings:" + JSON.toJSONString(RedisUtil.Strings.get(ZySysRoleMenu.class , "name")));

        //Hashs
        //RedisUtil.Hash.put("hashKey" , "ZySysRoleMenu" , list);
        //List<ZySysRoleMenu> resultHash = RedisUtil.Hash.get(ZySysRoleMenu.class, "hashKey", "ZySysRoleMenu");
        //LogUtil.info("Hashs:" + JSON.toJSONString(resultHash));

        //Lists
//        RedisUtil.Lists.leftPush("listKey" , menu1);
//        RedisUtil.Lists.leftPush("listKey" , menu2);
//        RedisUtil.Lists.leftPush("listKey" , menu3);
        //List<ZySysRoleMenu> resultList = RedisUtil.Lists.getList(ZySysRoleMenu.class,"listKey");
        //LogUtil.info("Lists:" + JSON.toJSONString(resultList));

        //Sets
        //RedisUtil.Sets.add("setKey" , menu1);
        //Set<ZySysRoleMenu> resultSet = RedisUtil.Sets.members(ZySysRoleMenu.class,"setKey");
        //LogUtil.info("Sets:" + JSON.toJSONString(resultSet));

        //ZSets
        //RedisUtil.SortSet.add("sortSetKey" , 1.0 , menu1);
        //RedisUtil.SortSet.add("sortSetKey" , 2.0 , menu2);
        //RedisUtil.SortSet.add("sortSetKey" , 3.0 , menu3);
        //Set<ZySysRoleMenu> resultSortSet = RedisUtil.SortSet.range(ZySysRoleMenu.class,"sortSetKey" , 1 , 3);
        //LogUtil.info("SortSet:" + JSON.toJSONString(resultSortSet));
    }


    @Test
    public void testStream(){
        List<ZySysRoleMenu> lists = new ArrayList<>();
        for(int i=0 ; i < 100; i++){
            String index = i + "";
            String menuId = i  % 10 == 0 ? "green" : "zhuyu";
            ZySysRoleMenu roleMenu = new ZySysRoleMenu();
            roleMenu.setId(index);
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(index);
            lists.add(roleMenu);
        }
        long d1 = System.currentTimeMillis();
        List<String> roleIds = lists.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(roleIds));
        //long i = lists.parallelStream().filter( item -> "green".equals(item.getMenuId())).count();
        //lists.parallelStream().filter( item -> "zhuyu".equals(item.getMenuId())).collect(Collectors.toList());
        //System.out.println("i:" + i + " , time:" + (System.currentTimeMillis() - d1));
    }

    @Test
    public void testRedisHash(){
        List<ZySysDict> dicts = DictCache.getDictsByPId("649036610398617600");
        System.out.println(dicts);
        RedisUtil.Hash.put(KeyConstant.Dict_Info , "666587637553332224" , "红包");
        RedisUtil.Hash.put(KeyConstant.Dict_Info , "666587637553332224" , "购物");
        String str = RedisUtil.Hash.get(KeyConstant.Dict_Parent_Info , "1");
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> ids = new ArrayList<>();
        if(!StringUtils.isEmpty(str)){
            ids = JSON.parseObject(str , type);
        }
        ids.add("2");
        RedisUtil.Hash.put(KeyConstant.Dict_Parent_Info , "1" , ids);

        str = RedisUtil.Hash.get(KeyConstant.Dict_Parent_Info , "1");
        List<String> ids2 = JSON.parseObject(str , type);
        ids2.add("3");

        RedisUtil.Hash.put(KeyConstant.Dict_Parent_Info , "1" , ids2);
    }*/
}

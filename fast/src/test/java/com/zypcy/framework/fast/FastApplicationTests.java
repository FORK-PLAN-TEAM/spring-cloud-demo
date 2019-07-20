package com.zypcy.framework.fast;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zypcy.framework.fast.common.response.PageModel;
import com.zypcy.framework.fast.common.util.LogUtil;
import com.zypcy.framework.fast.sys.entity.ZySysMenu;
import com.zypcy.framework.fast.sys.entity.ZySysRoleMenu;
import com.zypcy.framework.fast.sys.service.IZySysMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FastApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired private IZySysMenuService menuService;

    @Test
    public void testTree(){
        System.out.println(JSON.toJSONString(menuService.getMenuTrees()));
    }

    @Test
    public void pageList(){
        /*ZySysMenu menu = new ZySysMenu();
        menu.setIsdel(false);

        //current , size
        //pageindex pagesize
        Page<ZySysMenu> page = new Page();
        page.setCurrent(1);
        page.setSize(2);

        IPage<ZySysMenu> pageList = menuService.page(page , new QueryWrapper<>(menu));
        System.out.println(JSON.toJSONString(PageModel.convert(pageList)));
        */
    }

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
        ZySysRoleMenu menu1 = setZySysRoleMenu("16");
        ZySysRoleMenu menu2 = setZySysRoleMenu("17");
        ZySysRoleMenu menu3 = setZySysRoleMenu("18");
        //RedisUtil.Strings.set("name" , menu1);
        //LogUtil.info("Strings:" + JSON.toJSONString(RedisUtil.Strings.get(ZySysRoleMenu.class , "name")));

		List<ZySysRoleMenu> list = new ArrayList<>();
		for(int i=0; i < 10; i++){
			String index = i+"";
			list.add(setZySysRoleMenu(index));
		}
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

    private ZySysRoleMenu setZySysRoleMenu(String index) {
        ZySysRoleMenu roleMenu = new ZySysRoleMenu();
        roleMenu.setId(index);
        roleMenu.setMenuId(index);
        roleMenu.setRoleId(index);
        return roleMenu;
    }
}

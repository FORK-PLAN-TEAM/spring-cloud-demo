package com.zypcy.framework.fast.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.common.util.LogUtil;
import com.zypcy.framework.fast.sys.cache.DictCache;
import com.zypcy.framework.fast.sys.dto.ZySysTree;
import com.zypcy.framework.fast.sys.entity.ZySysDict;
import com.zypcy.framework.fast.sys.entity.ZySysOrganization;
import com.zypcy.framework.fast.sys.service.IZySysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 * <p>
 * getByParentId ，根据父级Id查询数据字典，只查询直属下级
 * getByType ， 根据type查询数据字典，会查询所有下级
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@Api(tags = "sys-数据字典")
@RestController
@RequestMapping("/sys/dict")
public class ZySysDictController {

    @Autowired
    IZySysDictService dictService;

    @ApiOperation(value = "数据字典列表页", notes = "页面", httpMethod = "GET")
    @GetMapping(value = "/list")
    public ModelAndView list(ModelMap map) {
        return new ModelAndView("sys/dict_list");
    }

    @ApiOperation(value = "打开查看、编辑页", notes = "页面", httpMethod = "GET")
    @GetMapping(value = "/edit")
    public ModelAndView edit(@ApiParam(value = "数据字典Id", required = true) String id, ModelMap map) {
        ZySysDict dict = new ZySysDict();
        if (!StringUtils.isEmpty(id)) {
            dict = DictCache.getDictById(id);//dictService.getById(id);
        }
        map.addAttribute("dict", dict);
        return new ModelAndView("sys/dict_edit");
    }

    @ApiOperation(value = "获取数据字典树", notes = "api接口", httpMethod = "GET")
    @GetMapping(value = "/getDictTrees")
    public List<ZySysTree> getDictTrees(@ApiParam(value = "数据字典Id", required = true) String id) {
        //前端异步获取数据字典
        return dictService.getDictTrees(id);
    }

    @ApiOperation(value = "根据Id获取字典信息", notes = "api接口", httpMethod = "GET")
    @GetMapping(value = "/getById")
    public ZySysDict getById(@ApiParam(value = "数据字典Id", required = true) String id) {
        //return dictService.getById(id);
        return DictCache.getDictById(id);
    }

    @ApiOperation(value = "根据ParentId获取字典信息，只获取下级字典", notes = "api接口", httpMethod = "GET")
    @GetMapping(value = "/getByParentId")
    public List<ZySysDict> getByParentId(@ApiParam(value = "数据字典ParentId", required = true) String parentId) {
        if (StringUtils.isEmpty(parentId)) {
            throw new BusinessException("请传入parentId");
        }
        //return dictService.getByParentId(parentId);
        return DictCache.getDictsByPId(parentId);
    }

    @ApiOperation(value = "根据Type获取字典信息，能获取多级字典", notes = "api接口", httpMethod = "GET")
    @GetMapping(value = "/getByType")
    public List<ZySysDict> getByType(@ApiParam(value = "数据字典type", required = true) String type) {
        if (StringUtils.isEmpty(type)) {
            throw new BusinessException("请传入type");
        }
        //根据type获取数据字典会把所有字典都取出来，因此过滤掉自己
        //List<ZySysDict> dicts = dictService.getByType(type);
        //if(dicts.size() > 0){
        //    dicts.remove(0);
        //}
        return dictService.getByType(type);
    }

    @ApiOperation(value = "获取数据字典列表", notes = "api接口", httpMethod = "GET")
    @GetMapping("pageList")
    public IPage<ZySysDict> pageList(String name, String type, String parentId, int pageIndex, int pageSize) {
        Page<ZySysDict> page = new Page<>(pageIndex, pageSize);
        ZySysDict dict = new ZySysDict();
        QueryWrapper<ZySysDict> wrapper = new QueryWrapper<>(dict);
        dict.setIsdel(false);
        if (!StringUtils.isEmpty(parentId)) {
            dict.setParentId(parentId);
        }
        if (!StringUtils.isEmpty(type)) {
            dict.setType(type);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        return dictService.page(page, wrapper);
    }

    @ApiOperation(value = "新增数据字典", notes = "api接口", httpMethod = "POST")
    @PostMapping(value = "/add")
    public String add(@ApiParam(value = "数据字典实体") @RequestBody ZySysDict dict) {
        String id = IdWorker.getId();
        dict.setId(id);
        dict.setIsdel(false);
        dict.setCreateTime(LocalDateTime.now());
        dict.setUpdateTime(LocalDateTime.now());
        dictService.save(dict);

        //加入到缓存
        DictCache.addDict(dict);
        return id;
    }

    @ApiOperation(value = "编辑数据字典", notes = "api接口", httpMethod = "POST")
    @PostMapping(value = "/edit")
    public boolean edit(@ApiParam(value = "数据字典实体") @RequestBody ZySysDict dict) {
        if (StringUtils.isEmpty(dict.getId()) || StringUtils.isEmpty(dict.getParentId()) || StringUtils.isEmpty(dict.getName())) {
            throw new BusinessException("请传入正确参数");
        }
        dict.setUpdateTime(LocalDateTime.now());

        //更新缓存
        DictCache.updateDict(dict);

        return dictService.updateById(dict);
    }

    @Transactional
    @ApiOperation(value = "删除数据字典", notes = "api接口", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public boolean del(@ApiParam(value = "数据字典Id", required = true) String id) {
        if (StringUtils.isEmpty(id)) {
            throw new BusinessException("请传入数据字典Id");
        }
        //通过type删除所有
        //先删除下级，再删除本级
        ZySysDict childDict = new ZySysDict();
        childDict.setParentId(id);
        int count = dictService.count(new QueryWrapper<>(childDict));
        if (count > 0) {
            dictService.delChild(id);
        }
        ZySysDict dict = dictService.getById(id);
        dict.setIsdel(true);

        //删除缓存
        DictCache.delDictById(id);

        return dictService.updateById(dict);
    }

}

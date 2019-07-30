package com.zypcy.framework.fast.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.response.ResultCodeEnum;
import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.common.util.LogUtil;
import com.zypcy.framework.fast.sys.dto.ZySysTree;
import com.zypcy.framework.fast.sys.entity.ZySysOrganization;
import com.zypcy.framework.fast.sys.service.IZySysOrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 组织机构 前端控制器
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@Api(tags = "Sys-组织机构")
@RestController
@RequestMapping("sys/organization")
public class ZySysOrganizationController {

    @Autowired private IZySysOrganizationService organizationService;

    /**
     * 返回组织机构列表页面
     * @return
     */
    @ApiOperation(value = "返回组织机构列表页面"  , notes = "页面", httpMethod = "GET")
    @GetMapping("list")
    public ModelAndView list(ModelMap map){
        map.addAttribute("orgTrees" , organizationService.getOrgTrees());
        return new ModelAndView("sys/organization_list");
    }

    @ApiOperation(value = "返回组织机构编辑、查看页面"  , notes = "页面", httpMethod = "GET")
    @GetMapping("edit")
    public ModelAndView edit(String orgId , ModelMap map){
        //新增传空对象，编辑则查询数据
        ZySysOrganization organization = new ZySysOrganization();
        if(!StringUtils.isEmpty(orgId)){
            organization = organizationService.getOrganizationById(orgId);
        }
        map.addAttribute("org" ,organization);
        return new ModelAndView("sys/organization_edit");
    }

    @ApiOperation(value = "选择组织机构树页面"  , notes = "页面", httpMethod = "GET")
    @GetMapping("selectOrg")
    public ModelAndView selectOrg(ModelMap map){
        map.addAttribute("orgTrees" , organizationService.getOrgTrees());
        return new ModelAndView("sys/organization_select");
    }


    @ApiOperation(value = "获取组织机构树"  , notes = "api接口", httpMethod = "POST")
    @PostMapping("getOrgTrees")
    public List<ZySysTree> getOrgTrees(){
        return organizationService.getOrgTrees();
    }

    @ApiOperation(value = "根据父级Id获取组织机构列表" , notes = "api接口", httpMethod = "GET")
    @GetMapping("pageList")
    public IPage<ZySysOrganization> pageList(String orgName , String parentId , int pageIndex , int pageSize){
        Page<ZySysOrganization> page = new Page<>(pageIndex , pageSize);
        ZySysOrganization organization = new ZySysOrganization();
        QueryWrapper<ZySysOrganization> wrapper = new QueryWrapper<>(organization);
        organization.setIsdel(false);
        if(!StringUtils.isEmpty(parentId)){
            organization.setParentId(parentId);
        }
        if(!StringUtils.isEmpty(orgName)){
            wrapper.like("org_name" , orgName);
        }
        return organizationService.page(page , wrapper);
    }

    @ApiOperation(value = "根据Id获取组织机构" , notes = "api接口", httpMethod = "GET")
    @GetMapping("getById")
    public ZySysOrganization getById(@ApiParam(value = "组织机构Id",required = true)String orgId){
        return organizationService.getOrganizationById(orgId);
    }

    @ApiOperation(value = "创建组织机构" , notes = "api接口", httpMethod = "POST")
    @PostMapping("add")
    public String add(@ApiParam(value = "组织机构实体") @RequestBody ZySysOrganization organization){
        organization.setOrgId(IdWorker.getId());
        int flag = organizationService.add(organization);
        if(!(flag > 0)){
            throw new BusinessException("操作失败，请重试");
        }
        return organization.getOrgId();
    }

    @ApiOperation(value = "修改组织机构" , notes = "api接口", httpMethod = "POST")
    @PostMapping("edit")
    public boolean update(@ApiParam(value = "组织机构实体") @RequestBody ZySysOrganization organization){
        if(StringUtils.isEmpty(organization.getOrgId()) || StringUtils.isEmpty(organization.getOrgName())){
            throw new BusinessException("请传入组织Id或组织机构名称");
        }
        organization.setUpdateTime(LocalDateTime.now());
        organization.setUpdateUserid(ContextHolder.getUserId());
        organization.setUpdateUsername(ContextHolder.getUserName());
        return organizationService.updateById(organization);
    }

    @ApiOperation(value = "删除组织机构" , notes = "api接口", httpMethod = "POST")
    @PostMapping("delete")
    public boolean delete(@ApiParam(value = "组织机构Id")String orgId){
        if(!StringUtils.isEmpty(orgId)){
            ZySysOrganization organization = organizationService.getById(orgId);
            if(organization != null){
                organization.setIsdel(true);
                organizationService.updateById(organization);
                return true;
            }else {
                throw new BusinessException(ResultCodeEnum.DATA_NOTFOUND,"请传入正确的组织机构Id");
            }
        }
        return false;
    }

    @ApiOperation(value = "批量删除组织机构" , notes = "api接口", httpMethod = "POST")
    @PostMapping("deleteBatch")
    public boolean deleteBatchByIds(@ApiParam(value = "组织机构Id字符串，以逗号分隔")String orgIds){
        String[] ids = orgIds.split(",");
        boolean flag = false;
        for(int i=0; i< ids.length ; i++){
            flag = organizationService.deleteOrgById(ids[i]);
        }
        return flag;
    }

}

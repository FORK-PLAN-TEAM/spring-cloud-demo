package com.zypcy.framework.fast.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.response.ResultCodeEnum;
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

import java.net.ContentHandler;
import java.time.LocalDateTime;

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
        map.addAttribute("sysUser" , ContextHolder.getUserInfo());
        return new ModelAndView("sys/organization_list");
    }





    @ApiOperation(value = "根据Id获取组织机构" , notes = "api接口", httpMethod = "GET")
    @GetMapping("getById")
    public ZySysOrganization getById(String orgId){
        ZySysOrganization organization = new ZySysOrganization();
        organization.setIsdel(false);
        organization.setOrgId(orgId);
        return organizationService.getOne(new QueryWrapper<>(organization));
    }

    @ApiOperation(value = "创建组织机构" , notes = "api接口", httpMethod = "POST")
    @PostMapping("create")
    public boolean add(@ApiParam(value = "组织机构实体") @RequestBody ZySysOrganization organization){
        return organizationService.add(organization) > 0 ? true : false;
    }

    @ApiOperation(value = "修改组织机构" , notes = "api接口", httpMethod = "POST")
    @PostMapping("update")
    public boolean update(@ApiParam(value = "组织机构实体") @RequestBody ZySysOrganization organization){
        if(!StringUtils.isEmpty(organization.getOrgId())){
            organization.setUpdateTime(LocalDateTime.now());
            return organizationService.updateById(organization);
        }
        return false;
    }

    @ApiOperation(value = "删除组织机构" , notes = "api接口", httpMethod = "POST")
    @PostMapping("delete")
    public boolean delete(String orgId){
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



}

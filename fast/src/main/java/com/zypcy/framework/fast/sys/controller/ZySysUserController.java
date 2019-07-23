package com.zypcy.framework.fast.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.response.ResultCodeEnum;
import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.sys.entity.ZySysOrganization;
import com.zypcy.framework.fast.sys.entity.ZySysUser;
import com.zypcy.framework.fast.sys.service.IZySysOrganizationService;
import com.zypcy.framework.fast.sys.service.IZySysUserRoleService;
import com.zypcy.framework.fast.sys.service.IZySysUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 * @author zhuyu
 * @since 2019-06-14
 */
@RestController
@RequestMapping("/sys/user")
public class ZySysUserController {

    @Autowired private IZySysUserService userService;
    @Autowired private IZySysOrganizationService organizationService;
    @Autowired private IZySysUserRoleService userRoleService;

    @ApiOperation(value = "用户列表页"  , notes = "页面", httpMethod = "GET")
    @GetMapping(value = "/list")
    public ModelAndView list(ModelMap map){
        map.addAttribute("orgTrees" , organizationService.getOrgTrees());
        return new ModelAndView("sys/user_list");
    }

    @ApiOperation(value = "返回用户编辑、查看页面"  , notes = "页面", httpMethod = "GET")
    @GetMapping("edit")
    public ModelAndView edit(String userId , ModelMap map){
        //新增传空对象，编辑则查询数据
        ZySysUser user = new ZySysUser();
        if(!StringUtils.isEmpty(userId)){
            user = getUserById(ContextHolder.getUserId());
        }
        map.addAttribute("user" ,user);
        map.addAttribute("roles" , userRoleService.getUserRoles(ContextHolder.getUserId()));
        return new ModelAndView("sys/user_edit");
    }

    @ApiOperation(value = "根据组织机构Id获取用户列表" , notes = "api接口", httpMethod = "GET")
    @GetMapping("pageList")
    public IPage<ZySysUser> pageList(String userName , String orgId , int pageIndex , int pageSize){
        Page<ZySysUser> page = new Page<>(pageIndex , pageSize);
        ZySysUser user = new ZySysUser();
        QueryWrapper<ZySysUser> wrapper = new QueryWrapper<>(user);
        user.setIsdel(false);
        if(!StringUtils.isEmpty(orgId)){
            user.setOrgId(orgId);
        }
        if(!StringUtils.isEmpty(userName)){
            wrapper.like("user_name" , userName);
        }
        return userService.page(page , wrapper);
    }


    @ApiOperation(value = "根据Id获取组织机构" , notes = "api接口", httpMethod = "GET")
    @GetMapping("getById")
    public ZySysUser getById(String userId){
        return getUserById(userId);
    }

    @ApiOperation(value = "创建用户" , notes = "api接口", httpMethod = "POST")
    @PostMapping("add")
    public String add(@ApiParam(value = "用户实体") @RequestBody ZySysUser user){
        if(!StringUtils.isEmpty(user.getRoleIds())){
            throw new BusinessException("请给用户选择相应角色");
        }
        user.setUserId(IdWorker.getId());
        int flag = userService.add(user);
        if(!(flag > 0)){
            throw new BusinessException("操作失败，请重试");
        }
        return user.getOrgId();
    }

    @ApiOperation(value = "修改用户" , notes = "api接口", httpMethod = "POST")
    @PostMapping("edit")
    public boolean update(@ApiParam(value = "用户实体") @RequestBody ZySysUser user){
        if(StringUtils.isEmpty(user.getUserId()) || StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getOrgId())){
            throw new BusinessException("请传入组织Id或用户Id或用户名称");
        }
        if(!StringUtils.isEmpty(user.getRoleIds())){
            throw new BusinessException("请给用户选择相应角色");
        }
        return userService.update(user);
    }

    @ApiOperation(value = "删除用户" , notes = "api接口", httpMethod = "POST")
    @PostMapping("delete")
    public boolean delete(String userId){
        if(!StringUtils.isEmpty(userId)){
            ZySysUser user = userService.getById(userId);
            if(user != null){
                user.setIsdel(true);
                userService.updateById(user);
                return true;
            }else {
                throw new BusinessException(ResultCodeEnum.DATA_NOTFOUND,"请传入正确的用户Id");
            }
        }
        return false;
    }

    @ApiOperation(value = "批量删除用户" , notes = "api接口", httpMethod = "POST")
    @PostMapping("deleteBatch")
    public boolean deleteBatchByIds(String userIds){
        String[] ids = userIds.split(",");
        boolean flag = false;
        for(int i=0; i< ids.length ; i++){
            flag = userService.deleteOrgById(ids[i]);
        }
        return flag;
    }

    //获取未删除的用户ID
    private ZySysUser getUserById(String userId){
        ZySysUser user = new ZySysUser();
        user.setUserId(userId);
        user.setIsdel(false);
        return userService.getOne(new QueryWrapper<>(user));
    }
}

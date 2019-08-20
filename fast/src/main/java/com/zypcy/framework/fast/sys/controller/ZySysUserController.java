package com.zypcy.framework.fast.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.response.ResultCodeEnum;
import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.sys.cache.UserLoginCache;
import com.zypcy.framework.fast.sys.dto.ZySysLoginInfo;
import com.zypcy.framework.fast.sys.entity.ZySysOrganization;
import com.zypcy.framework.fast.sys.entity.ZySysRole;
import com.zypcy.framework.fast.sys.entity.ZySysUser;
import com.zypcy.framework.fast.sys.service.IZySysOrganizationService;
import com.zypcy.framework.fast.sys.service.IZySysUserRoleService;
import com.zypcy.framework.fast.sys.service.IZySysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 * @author zhuyu
 * @since 2019-06-14
 */
@Api(tags = "sys-用户管理")
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
            user = getUserById(userId);

            //查询当前用户所拥有的角色Id
            List<String> roleIds = new ArrayList<>();
            userRoleService.getUserRoles(userId).forEach( zySysRole -> roleIds.add(zySysRole.getRoleId()));
            map.addAttribute("userRoleIds", roleIds);
        }
        map.addAttribute("user" ,user);
        //查询当前用户所创建的角色集合
        map.addAttribute("roles" , userRoleService.getUserCreateRoles(ContextHolder.getUserId()));
        return new ModelAndView("sys/user_edit");
    }

    @ApiOperation(value = "修改密码"  , notes = "页面", httpMethod = "GET")
    @GetMapping(value = "/updatePwd")
    public ModelAndView updatePwd(){
        return new ModelAndView("sys/user_update_pwd");
    }


    @ApiOperation(value = "根据组织机构Id获取用户列表" , notes = "api接口", httpMethod = "GET")
    @GetMapping("pageList")
    public IPage<ZySysUser> pageList(@ApiParam(value = "用户名")String userName , @ApiParam(value = "组织Id")String orgId , @ApiParam(value = "页码")int pageIndex , @ApiParam(value = "每页数据量")int pageSize){
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
        //如果是超级管理员，则看所有，否则查看自己创建的用户
        if(!"admin".equals(ContextHolder.getUserId())){
            user.setCreateUserid(ContextHolder.getUserId());
        }
        return userService.page(page , wrapper);
    }


    @ApiOperation(value = "根据Id获取用户信息" , notes = "api接口", httpMethod = "GET")
    @GetMapping("getById")
    public ZySysUser getById(@ApiParam(value = "用户Id")String userId){
        return getUserById(userId);
    }

    @ApiOperation(value = "根据Token获取用户信息" , notes = "api接口", httpMethod = "GET")
    @GetMapping()
    public ZySysUser getByToken(){
        return ContextHolder.getSysUser();
    }

    @ApiOperation(value = "创建用户" , notes = "api接口", httpMethod = "POST")
    @PostMapping("add")
    public String add(@ApiParam(value = "用户实体") @RequestBody ZySysUser user){
        if(StringUtils.isEmpty(user.getRoleIds())){
            throw new BusinessException("请给用户选择相应角色");
        }
        //验证该帐号未注册
        if(userService.existsUserAccount(user.getUserAccount())){
            throw new BusinessException("登录帐号已被使用，请输入新的登录帐号");
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
        if(StringUtils.isEmpty(user.getRoleIds())){
            throw new BusinessException("请给用户选择相应角色");
        }
        return userService.update(user);
    }

    @ApiOperation(value = "修改密码" , notes = "api接口", httpMethod = "POST")
    @PostMapping("updatePwd")
    public boolean updatePwd(@ApiParam(value = "旧密码") String oldPwd ,@ApiParam(value = "新密码") String newPwd){
        if(StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)){
            throw new BusinessException("请传入旧密码或新密码");
        }
        if(oldPwd.equals(newPwd)){
            throw new BusinessException("旧密码与新密码不能相同");
        }
        return userService.updatePwd(oldPwd , newPwd);
    }

    @ApiOperation(value = "删除用户" , notes = "api接口", httpMethod = "POST")
    @PostMapping("delete")
    public boolean delete(@ApiParam(value = "用户Id")String userId){
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
    public boolean deleteBatchByIds(@ApiParam(value = "用户Id字符串，以逗号分隔")String userIds){
        String[] ids = userIds.split(",");
        boolean flag = false;
        for(int i=0; i< ids.length ; i++){
            flag = userService.deleteById(ids[i]);
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

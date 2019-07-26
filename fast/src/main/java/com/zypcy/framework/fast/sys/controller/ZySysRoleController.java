package com.zypcy.framework.fast.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.response.ResultCodeEnum;
import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.sys.entity.ZySysRole;
import com.zypcy.framework.fast.sys.service.IZySysRoleService;
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
 * 角色 前端控制器
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@RestController
@RequestMapping("/sys/role")
public class ZySysRoleController {

    @Autowired private IZySysRoleService roleService;

    @ApiOperation(value = "角色列表页"  , notes = "页面", httpMethod = "GET")
    @GetMapping(value = "/list")
    public ModelAndView list(ModelMap map){
        return new ModelAndView("sys/role_list");
    }

    @ApiOperation(value = "返回角色编辑、查看页面"  , notes = "页面", httpMethod = "GET")
    @GetMapping("edit")
    public ModelAndView edit(String roleId , ModelMap map){
        //新增传空对象，编辑则查询数据
        ZySysRole role = new ZySysRole();
        if(!StringUtils.isEmpty(roleId)){
            role = getRoleById(roleId);
        }
        map.addAttribute("role" ,role);
        return new ModelAndView("sys/role_edit");
    }

    @ApiOperation(value = "获取角色列表" , notes = "api接口", httpMethod = "GET")
    @GetMapping("pageList")
    public IPage<ZySysRole> pageList(int pageIndex , int pageSize){
        Page<ZySysRole> page = new Page<>(pageIndex , pageSize);
        ZySysRole role = new ZySysRole();
        role.setIsdel(false);
        role.setCreateUserid(ContextHolder.getUserId());//只能看到自己创建的角色
        return roleService.page(page , new QueryWrapper<>(role));
    }


    @ApiOperation(value = "根据Id获取角色信息" , notes = "api接口", httpMethod = "GET")
    @GetMapping("getById")
    public ZySysRole getById(String roleId){
        return getRoleById(roleId);
    }

    @ApiOperation(value = "创建角色" , notes = "api接口", httpMethod = "POST")
    @PostMapping("add")
    public String add(@ApiParam(value = "角色实体") @RequestBody ZySysRole role){
        role.setRoleId(IdWorker.getId());
        int flag = roleService.add(role);
        if(!(flag > 0)){
            throw new BusinessException("操作失败，请重试");
        }
        return role.getRoleId();
    }

    @ApiOperation(value = "修改角色" , notes = "api接口", httpMethod = "POST")
    @PostMapping("edit")
    public boolean update(@ApiParam(value = "角色实体") @RequestBody ZySysRole role){
        if(StringUtils.isEmpty(role.getRoleId()) || StringUtils.isEmpty(role.getRoleName())){
            throw new BusinessException("请传入角色Id或角色名称");
        }
        role.setUpdateTime(LocalDateTime.now());
        role.setUpdateUserid(ContextHolder.getUserId());
        role.setUpdateUsername(ContextHolder.getUserName());
        return roleService.updateById(role);
    }

    @ApiOperation(value = "删除角色" , notes = "api接口", httpMethod = "POST")
    @PostMapping("delete")
    public boolean delete(String roleId){
        if(!StringUtils.isEmpty(roleId)){
            ZySysRole role = getById(roleId);
            if(role != null){
                role.setIsdel(true);
                roleService.updateById(role);
                return true;
            }else {
                throw new BusinessException(ResultCodeEnum.DATA_NOTFOUND,"请传入正确的Id");
            }
        }
        return false;
    }

    @ApiOperation(value = "批量删除角色" , notes = "api接口", httpMethod = "POST")
    @PostMapping("deleteBatch")
    public boolean deleteBatchByIds(String roleIds){
        String[] ids = roleIds.split(",");
        boolean flag = false;
        for(int i=0; i< ids.length ; i++){
            flag = roleService.deleteOrgById(ids[i]);
        }
        return flag;
    }

    //获取未删除的角色
    private ZySysRole getRoleById(String roleId){
        ZySysRole role = new ZySysRole();
        role.setRoleId(roleId);
        role.setIsdel(false);
        return roleService.getOne(new QueryWrapper<>(role));
    }
}

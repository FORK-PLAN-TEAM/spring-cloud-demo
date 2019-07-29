package com.zypcy.framework.fast.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zypcy.framework.fast.bus.service.ICashbookService;
import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.response.ResultCodeEnum;
import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.sys.entity.ZySysRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.zypcy.framework.fast.bus.entity.Cashbook;

import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

/**
 * <p>
 * 记账本 前端控制器
 * </p>
 *
 * @author zhuyu
 * @since 2019-07-29
 */
@Api(tags = "记账本")
@RestController
@RequestMapping("/bus/cashbook")
public class CashbookController {

    @Autowired private ICashbookService cashbookService;

    @ApiOperation(value = "记账本列表页面"  , notes = "页面", httpMethod = "GET")
    @GetMapping("list")
    public ModelAndView list(){
        return new ModelAndView("bus/cashbook/list");
    }

    @ApiOperation(value = "返回记账本编辑、查看页面"  , notes = "页面", httpMethod = "GET")
    @GetMapping("edit")
    public ModelAndView edit(String cashId , ModelMap map){
        //新增传空对象，编辑则查询数据
        Cashbook cashbook = new Cashbook();
        if(!StringUtils.isEmpty(cashId)){
            cashbook = getCashbookById(cashId);
        }
        map.addAttribute("cashbook" ,cashbook);
        return new ModelAndView("bus/cashbook/edit");
    }


    @ApiOperation(value = "获取记账本列表" , notes = "api接口", httpMethod = "GET")
    @GetMapping("pageList")
    public IPage<Cashbook> pageList(String startTime , String endTime,int pageIndex , int pageSize){
        Page<Cashbook> page = new Page<>(pageIndex , pageSize);
        Cashbook cashbook = new Cashbook();
        cashbook.setIsdel(false);
        cashbook.setCreateUserid(ContextHolder.getUserId());//只能看自己的信息
        //按时间查询

        return cashbookService.page(page , new QueryWrapper<>(cashbook));
    }

    @ApiOperation(value = "根据Id获取账目信息" , notes = "api接口", httpMethod = "GET")
    @GetMapping("getById")
    public Cashbook getById(String cashId){
        return getCashbookById(cashId);
    }

    @ApiOperation(value = "创建账目" , notes = "api接口", httpMethod = "POST")
    @PostMapping("add")
    public String add(@ApiParam(value = "角色实体") @RequestBody Cashbook cashbook){
        cashbook.setCashId(IdWorker.getId());
        int flag = cashbookService.add(cashbook);
        if(!(flag > 0)){
            throw new BusinessException("操作失败，请重试");
        }
        return cashbook.getCashId();
    }

    @ApiOperation(value = "修改账目" , notes = "api接口", httpMethod = "POST")
    @PostMapping("edit")
    public boolean update(@ApiParam(value = "记账本实体") @RequestBody Cashbook cashbook){
        if(StringUtils.isEmpty(cashbook.getCashId())){
            throw new BusinessException("请传入账目Id");
        }
        cashbook.setUpdateTime(LocalDateTime.now());
        cashbook.setUpdateUserid(ContextHolder.getUserId());
        cashbook.setUpdateUsername(ContextHolder.getUserName());
        return cashbookService.updateById(cashbook);
    }

    @ApiOperation(value = "删除账目" , notes = "api接口", httpMethod = "POST")
    @PostMapping("delete")
    public boolean delete(String cashId){
        if(!StringUtils.isEmpty(cashId)){
            Cashbook role = getCashbookById(cashId);
            if(role != null){
                role.setIsdel(true);
                cashbookService.updateById(role);
                return true;
            }else {
                throw new BusinessException(ResultCodeEnum.DATA_NOTFOUND,"请传入正确的Id");
            }
        }
        return false;
    }

    @ApiOperation(value = "批量删除账目" , notes = "api接口", httpMethod = "POST")
    @PostMapping("deleteBatch")
    public boolean deleteBatchByIds(String cashIdIds){
        String[] ids = cashIdIds.split(",");
        boolean flag = false;
        for(int i=0; i< ids.length ; i++){
            flag = cashbookService.deleteBatchById(ids[i]);
        }
        return flag;
    }

    //获取未删除的账目信息
    private Cashbook getCashbookById(String cashId){
        Cashbook cashbook = new Cashbook();
        cashbook.setCashId(cashId);
        cashbook.setIsdel(false);
        return cashbookService.getOne(new QueryWrapper<>(cashbook));
    }
}

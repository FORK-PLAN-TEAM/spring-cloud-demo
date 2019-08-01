package com.zypcy.framework.fast.bus.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zypcy.framework.fast.bus.service.ICashbookService;
import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.response.ResultCodeEnum;
import com.zypcy.framework.fast.common.util.ExcelUtil;
import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.common.util.WordUtil;
import com.zypcy.framework.fast.sys.entity.ZySysRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.var;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.zypcy.framework.fast.bus.entity.Cashbook;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private ICashbookService cashbookService;

    @ApiOperation(value = "记账本列表页面", notes = "页面", httpMethod = "GET")
    @GetMapping("list")
    public ModelAndView list(ModelMap map) {
        map.addAttribute("totalAmount", cashbookService.getTotalAmount(ContextHolder.getUserId()));
        return new ModelAndView("bus/cashbook/list");
    }

    @ApiOperation(value = "返回记账本编辑、查看页面", notes = "页面", httpMethod = "GET")
    @GetMapping("edit")
    public ModelAndView edit(String cashId, ModelMap map) {
        //新增传空对象，编辑则查询数据
        Cashbook cashbook = new Cashbook();
        if (!StringUtils.isEmpty(cashId)) {
            cashbook = getCashbookById(cashId);
        }
        map.addAttribute("cashbook", cashbook);
        return new ModelAndView("bus/cashbook/edit");
    }


    @ApiOperation(value = "获取记账本列表", notes = "api接口", httpMethod = "GET")
    @GetMapping("pageList")
    public IPage<Cashbook> pageList(@ApiParam(value = "开始时间") String startTime, @ApiParam(value = "结束时间") String endTime, int pageIndex, int pageSize) {
        return cashbookService.pageList(startTime, endTime, pageIndex, pageSize);
    }

    @ApiOperation(value = "根据Id获取账目信息", notes = "api接口", httpMethod = "GET")
    @GetMapping("getById")
    public Cashbook getById(String cashId) {
        return getCashbookById(cashId);
    }

    @ApiOperation(value = "创建账目", notes = "api接口", httpMethod = "POST")
    @PostMapping("add")
    public String add(@ApiParam(value = "角色实体") @RequestBody Cashbook cashbook) {
        cashbook.setCashId(IdWorker.getId());
        int flag = cashbookService.add(cashbook);
        if (!(flag > 0)) {
            throw new BusinessException("操作失败，请重试");
        }
        return cashbook.getCashId();
    }

    @ApiOperation(value = "修改账目", notes = "api接口", httpMethod = "POST")
    @PostMapping("edit")
    public boolean update(@ApiParam(value = "记账本实体") @RequestBody Cashbook cashbook) {
        if (StringUtils.isEmpty(cashbook.getCashId())) {
            throw new BusinessException("请传入账目Id");
        }
        cashbook.setUpdateTime(LocalDateTime.now());
        cashbook.setUpdateUserid(ContextHolder.getUserId());
        cashbook.setUpdateUsername(ContextHolder.getUserName());
        return cashbookService.updateById(cashbook);
    }

    @ApiOperation(value = "删除账目", notes = "api接口", httpMethod = "POST")
    @PostMapping("delete")
    public boolean delete(String cashId) {
        if (!StringUtils.isEmpty(cashId)) {
            Cashbook role = getCashbookById(cashId);
            if (role != null) {
                role.setIsdel(true);
                cashbookService.updateById(role);
                return true;
            } else {
                throw new BusinessException(ResultCodeEnum.DATA_NOTFOUND, "请传入正确的Id");
            }
        }
        return false;
    }

    @ApiOperation(value = "批量删除账目", notes = "api接口", httpMethod = "POST")
    @PostMapping("deleteBatch")
    public boolean deleteBatchByIds(String cashIds) {
        String[] ids = cashIds.split(",");
        boolean flag = false;
        for (int i = 0; i < ids.length; i++) {
            flag = cashbookService.deleteBatchById(ids[i]);
        }
        return flag;
    }

    //获取未删除的账目信息
    private Cashbook getCashbookById(String cashId) {
        Cashbook cashbook = new Cashbook();
        cashbook.setCashId(cashId);
        cashbook.setIsdel(false);
        return cashbookService.getOne(new QueryWrapper<>(cashbook));
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出账目信息", notes = "api接口", httpMethod = "GET")
    public void export(HttpServletResponse response, String startTime, String endTime) throws IOException {
        WordUtil.testReplaceDoc();
        String userId = ContextHolder.getUserId();
        Cashbook cashbook = new Cashbook();
        cashbook.setIsdel(false);
        cashbook.setIsdel(false);
        if(!"admin".equals(userId)){
            cashbook.setCreateUserid(userId);
        }
        QueryWrapper<Cashbook> wrapper = new QueryWrapper<>(cashbook);
        wrapper.orderByDesc("record_time");
        if(!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)){
            wrapper.between("record_time", startTime , endTime);
        }else if(!StringUtils.isEmpty(startTime)){
            wrapper.between("record_time", startTime , startTime + " 23:59:59");
        }
        //得到要导出的list集合
        List<Cashbook> list = cashbookService.list(wrapper);
        String[] title = {"记录时间", "入账内容", "账目金额", "备注"};
        List<String[]> exportDataList = list.stream().map(item -> new String[]{
                DateUtil.format(item.getRecordTime() , "yyyy-MM-dd"),
                item.getCashDetail(),
                item.getAmount().toString(),
                item.getRemark()
        }).collect(Collectors.toList());
        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook("账目信息", title, exportDataList.toArray(new String[list.size()][title.length]), null);
        workbook.write(response.getOutputStream());
        ExcelUtil.setResponseStream(response, "账目信息" +IdWorker.getDateId()+ ".xlsx");
    }
}

package com.zypcy.framework.fast.bus.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zypcy.framework.fast.bus.dto.CashbookSaveDto;
import com.zypcy.framework.fast.bus.service.ICashbookService;
import com.zypcy.framework.fast.bus.service.ICashbookStatisticsService;
import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.common.error.BusinessException;
import com.zypcy.framework.fast.common.response.ResultCodeEnum;
import com.zypcy.framework.fast.common.util.ExcelUtil;
import com.zypcy.framework.fast.common.util.IdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.zypcy.framework.fast.bus.entity.Cashbook;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 记账本 前端控制器
 * @author zhuyu
 * @since 2019-07-29
 */
@Api(tags = "bus-记账本")
@RestController
@RequestMapping("/bus/cashbook")
public class CashBookController {

    @Autowired
    private ICashbookService cashbookService;
    @Autowired
    private ICashbookStatisticsService statisticsService;
    @Autowired
    private ModelMapper modelMapper;

    @ApiOperation(value = "记账本列表页面", notes = "页面", httpMethod = "GET")
    @GetMapping("list")
    public ModelAndView list(ModelMap map) {
        map.addAttribute("amounts", cashbookService.getCurrentDayAmount(ContextHolder.getUserId()));
        map.addAttribute("userId" , ContextHolder.getUserId());
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

    @ApiOperation(value = "返回记账本统计页面", notes = "页面", httpMethod = "GET")
    @GetMapping("statistics")
    public ModelAndView statistics(){
        return new ModelAndView("bus/cashbook/statistics");
    }

    @ApiOperation(value = "获取记账本列表", notes = "api接口", httpMethod = "GET")
    @GetMapping("pageList")
    public IPage<Cashbook> pageList(@ApiParam(value = "账目类型") String cashType, @ApiParam(value = "开始时间") String startTime, @ApiParam(value = "结束时间") String endTime,
                                  @ApiParam(value = "账本详情-数据字典id") String dictId,int pageIndex, int pageSize) {
        return cashbookService.pageList(cashType , startTime, endTime,dictId, pageIndex, pageSize);
    }

    @ApiOperation(value = "根据Id获取账目信息", notes = "api接口", httpMethod = "GET")
    @GetMapping("getById")
    public Cashbook getById(String cashId) {
        return getCashbookById(cashId);
    }

    @ApiOperation(value = "创建账目", notes = "api接口", httpMethod = "POST")
    @PostMapping("add")
    public String add(@ApiParam(value = "角色实体") @Valid @RequestBody CashbookSaveDto cashbookDto) {
        Cashbook cashbook = modelMapper.map(cashbookDto , Cashbook.class);
        cashbook.setCashId(IdWorker.getId());
        int flag = cashbookService.add(cashbook);
        if (!(flag > 0)) {
            throw new BusinessException("操作失败，请重试");
        }
        statisticsService.addByCashbook(cashbook);//记录统计信息
        return cashbook.getCashId();
    }

    @ApiOperation(value = "修改账目", notes = "api接口", httpMethod = "POST")
    @PostMapping("edit")
    public String edit(@ApiParam(value = "记账本实体") @Valid @RequestBody CashbookSaveDto cashbookDto) {
        Cashbook cashbook = modelMapper.map(cashbookDto , Cashbook.class);
        if (StringUtils.isEmpty(cashbook.getCashId())) {
            throw new BusinessException("请传入账目Id");
        }
        Cashbook oldCashbook = getById(cashbook.getCashId());
        if(oldCashbook == null){
            throw new BusinessException("请传入正确的账目Id");
        }
        cashbook.setUpdateTime(LocalDateTime.now());
        cashbook.setUpdateUserid(ContextHolder.getUserId());
        cashbook.setUpdateUsername(ContextHolder.getUserName());
        cashbookService.updateById(cashbook);
        statisticsService.updateByCashbook(oldCashbook , cashbook);//更新统计信息
        return cashbook.getCashId();
    }

    @ApiOperation(value = "删除账目", notes = "api接口", httpMethod = "POST")
    @PostMapping("delete")
    public boolean delete(String cashId) {
        if (!StringUtils.isEmpty(cashId)) {
            Cashbook cashbook = getCashbookById(cashId);
            if (cashbook != null) {
                cashbook.setIsdel(true);
                cashbookService.updateById(cashbook);
                statisticsService.deleteByCashbook(cashbook);//删除统计信息
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

    @ApiOperation(value = "统计一段时间段内的记账金额", notes = "api接口", httpMethod = "POST")
    @PostMapping("getTimeSlotAmount")
    public Map<String,String> getTimeSlotAmount(@ApiParam(value = "开始时间") String startTime, @ApiParam(value = "结束时间") String endTime){
        if(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)){
            throw new BusinessException("请传入开始或结束时间");
        }
        return cashbookService.getTimeSlotAmount(ContextHolder.getUserId() , startTime , endTime);
    }

    @GetMapping(value = "/export")
    @ApiOperation(value = "导出账目信息", notes = "api接口", httpMethod = "GET")
    public void export(HttpServletResponse response, String startTime, String endTime) throws IOException {
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
        String[] title = {"账目类型" ,"记录时间", "入账类别", "账目金额", "备注"};
        List<String[]> exportDataList = list.stream().map(item -> new String[]{
                item.getCashType().equals("0") ? "支出" : "收入" ,
                DateUtil.format(item.getRecordTime() , "yyyy-MM-dd"),
                item.getCashCategory(),
                item.getAmount().toString(),
                item.getRemark()
        }).collect(Collectors.toList());
        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook("账目信息", title, exportDataList, null);
        ExcelUtil.setResponseStream(response, "账目信息" +IdWorker.getDateId()+ ".xls");
        workbook.write(response.getOutputStream());
    }
}

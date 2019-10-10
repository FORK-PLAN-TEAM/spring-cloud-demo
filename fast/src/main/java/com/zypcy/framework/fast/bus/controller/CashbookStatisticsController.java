package com.zypcy.framework.fast.bus.controller;

import com.zypcy.framework.fast.bus.dto.CashbookStatisticsDto;
import com.zypcy.framework.fast.bus.entity.CashbookStatistics;
import com.zypcy.framework.fast.bus.service.ICashbookStatisticsService;
import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.common.error.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * 定时任务统计用户不同月份-不同类型-不同类别数据 前端控制器
 *
 * @author zhuyu
 * @since 2019-10-09
 */
@Api(tags = "bus-记账本统计")
@RestController
@RequestMapping("/bus/cashbook/statistics")
public class CashbookStatisticsController {

    @Autowired
    private ICashbookStatisticsService statisticsService;

    @ApiOperation(value = "按月统计页面", notes = "页面", httpMethod = "GET")
    @GetMapping("list")
    public ModelAndView list(ModelMap map) {
        return new ModelAndView("bus/cashbook/statistics/list");
    }

    @ApiOperation(value = "按条件返回统计数据", notes = "api接口", httpMethod = "POST")
    @PostMapping("data")
    public CashbookStatisticsDto data(@ApiParam(value = "开始时间") String startTime, @ApiParam(value = "结束时间") String endTime) throws InterruptedException, ExecutionException {
        if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
            throw new BusinessException("请传入开始或结束时间");
        }
        //异步执行三个统计方法
        Future<List<CashbookStatistics>> fmonths = statisticsService.statisticsByMonth(ContextHolder.getUserId(), startTime, endTime);
        Future<List<CashbookStatistics>> fcategorys = statisticsService.statisticsByCategory(ContextHolder.getUserId(), startTime, endTime);

        CashbookStatisticsDto statisticsDto = new CashbookStatisticsDto();
        List<CashbookStatistics> months = fmonths.get();
        List<CashbookStatistics> categorys = fcategorys.get();
        statisticsDto.setMonths(months);
        statisticsDto.setCategorys(categorys);
        return statisticsDto;
    }
}

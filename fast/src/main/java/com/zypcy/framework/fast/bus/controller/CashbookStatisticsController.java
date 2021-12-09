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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


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
        //异步执行统计方法
        Future<List<CashbookStatistics>> fmonths = statisticsService.statisticsByMonth(ContextHolder.getUserId(), startTime, endTime);
        Future<List<CashbookStatistics>> fcategorys = statisticsService.statisticsByCategory(ContextHolder.getUserId(), startTime, endTime);

        CashbookStatisticsDto statisticsDto = new CashbookStatisticsDto();
        List<CashbookStatistics> months = fmonths.get();
        List<CashbookStatistics> categorys = fcategorys.get();

        statisticsDto.setMonths(listHandle(months));
        statisticsDto.setCategorys(categorys);
        return statisticsDto;
    }

    /**
     * 数据处理，收入支出数据行转列
     * cashType = 0:支出，1:收入
     *
     * @param list
     * @return
     */
    private List<CashbookStatistics> listHandle(List<CashbookStatistics> list) {
        List<CashbookStatistics> zhichu = list.stream().filter(statis -> "0".equals(statis.getCashType())).collect(Collectors.toList());
        List<CashbookStatistics> shouru = list.stream().filter(statis -> "1".equals(statis.getCashType())).collect(Collectors.toList());

        List<CashbookStatistics> data = new ArrayList<>();
        //循环取出支出列表中的年月数据，判断收入列表是否有支出的年月，有则设置收入金额，否则收入金额为0
        zhichu.forEach(i -> {
            boolean iflag = data.stream().anyMatch(idata -> idata.getSyear().equals(i.getSyear()) && idata.getSmonth().equals(i.getSmonth()));
            if (!iflag) {
                Optional<CashbookStatistics> js = shouru.stream().filter(jdata -> jdata.getSyear().equals(i.getSyear()) && jdata.getSmonth().equals(i.getSmonth())).findAny();
                if (js.isPresent()) {
                    i.setExtendAmount(js.get().getAmount());
                } else {
                    i.setExtendAmount(0.00);
                }
                data.add(i);
            }
        });
        //再循环取出收入列表年月数据，判断总数据中是否有收入的年月，有则不管，没有则添加到总数据列表中，且设置收入金额，把支出金额设为0
        shouru.forEach(i -> {
            boolean iflag = data.stream().anyMatch(idata -> idata.getSyear().equals(i.getSyear()) && idata.getSmonth().equals(i.getSmonth()));
            if (!iflag) {
                i.setExtendAmount(i.getAmount());
                i.setAmount(0.00);
                data.add(i);
            }
        });
        return data;
    }
}

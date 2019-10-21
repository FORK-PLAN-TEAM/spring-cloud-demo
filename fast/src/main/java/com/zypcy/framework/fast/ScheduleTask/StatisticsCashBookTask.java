package com.zypcy.framework.fast.ScheduleTask;

import cn.hutool.core.date.DateUtil;
import com.zypcy.framework.fast.bus.entity.Cashbook;
import com.zypcy.framework.fast.bus.service.ICashbookService;
import com.zypcy.framework.fast.bus.service.ICashbookStatisticsService;
import com.zypcy.framework.fast.common.util.LogUtil;
import com.zypcy.framework.fast.sys.service.IZySysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

/**
 * 统计记账本数据，统计用户不同月份-不同类型-不同类别数据
 */
@Configuration
public class StatisticsCashBookTask {

    @Autowired
    private IZySysUserService userService;
    @Autowired
    private ICashbookService cashbookService;
    @Autowired
    private ICashbookStatisticsService statisticsService;

    /**
     * 定时任务，每天凌晨1点执行一次
     * 统计每个用户，某天、不同类型、不同类别的账目数据

    @Scheduled(cron = "0 0 1 * * ?")*/
    public void statisticsCashBook() {
        /*String yesterDay = DateUtil.offsetDay(new Date() , -1).toString("yyyyMMdd");//昨天
        userService.listAllUserId().stream().forEach(id -> {
            LogUtil.info("userId:" + id + " , yesterDay:" + yesterDay);
            //统计结果cashbooks：recordTime、cashType、dictId、cashCategory、amount
            //List<Cashbook> cashbooks = cashbookService.statisticsDayAmount(id , yesterDay);
            //保存每人每天的账目数据到统计表
            //statisticsService.saveOrUpdate(id , cashbooks);

            statisticsService.testTaskSaveData(id , yesterDay);
        });*/
    }

}

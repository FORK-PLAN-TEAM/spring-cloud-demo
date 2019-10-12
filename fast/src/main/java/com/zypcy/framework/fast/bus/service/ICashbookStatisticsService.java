package com.zypcy.framework.fast.bus.service;

import com.zypcy.framework.fast.bus.entity.Cashbook;
import com.zypcy.framework.fast.bus.entity.CashbookStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.concurrent.Future;

/**
 * 定时任务统计用户不同月份-不同类型-不同类别数据 服务类
 * @author zhuyu
 * @since 2019-10-09
 */
public interface ICashbookStatisticsService extends IService<CashbookStatistics> {

    void testTaskSaveData(String userId ,String yesterDay);

    /**
     * 新增或更新累加账目统计数据
     * @param userId
     * @param cashbooks
     */
    void saveOrUpdate(String userId , List<Cashbook> cashbooks);

    /**
     * 按月统计用户账目数据
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    Future<List<CashbookStatistics>> statisticsByMonth(String userId , String startTime, String endTime);

    /**
     * 按数据字典类别统计用户账目数据
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    Future<List<CashbookStatistics>> statisticsByCategory(String userId, String startTime, String endTime);

}

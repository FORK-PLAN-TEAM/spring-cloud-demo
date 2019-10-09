package com.zypcy.framework.fast.bus.service;

import com.zypcy.framework.fast.bus.entity.Cashbook;
import com.zypcy.framework.fast.bus.entity.CashbookStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 定时任务统计用户不同月份-不同类型-不同类别数据 服务类
 * @author zhuyu
 * @since 2019-10-09
 */
public interface ICashbookStatisticsService extends IService<CashbookStatistics> {

    /**
     * 新增或更新累加账目统计数据
     * @param userId
     * @param cashbooks
     */
    void saveOrUpdate(String userId , List<Cashbook> cashbooks);
}

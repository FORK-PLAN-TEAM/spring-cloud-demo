package com.zypcy.framework.fast.bus.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zypcy.framework.fast.bus.dto.CashbookShouZhiDto;
import com.zypcy.framework.fast.bus.entity.Cashbook;
import com.zypcy.framework.fast.bus.entity.CashbookStatistics;
import com.zypcy.framework.fast.bus.mapper.CashbookStatisticsMapper;
import com.zypcy.framework.fast.bus.service.ICashbookStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.common.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

/**
 * 定时任务统计用户不同月份-不同类型-不同类别数据 服务实现类
 * @author zhuyu
 * @since 2019-10-09
 */
@Service
public class CashbookStatisticsServiceImpl extends ServiceImpl<CashbookStatisticsMapper, CashbookStatistics> implements ICashbookStatisticsService {

    @Autowired
    private CashbookStatisticsMapper statisticsMapper;

    private CashbookStatistics initCashbookStatistics(String userId , Cashbook cashbook){
        CashbookStatistics statistics = new CashbookStatistics();
        statistics.setSid(IdWorker.getDateId());
        statistics.setCreateUserid(userId);
        statistics.setSyear(DateUtil.year(cashbook.getRecordTime()));
        statistics.setSmonth(DateUtil.month(cashbook.getRecordTime()) + 1);
        statistics.setCashType(cashbook.getCashType());
        statistics.setDictId(cashbook.getDictId());
        statistics.setCashCategory(cashbook.getCashCategory());
        statistics.setAmount(cashbook.getAmount());
        return statistics;
    }

    //更新，累加账目统计数据
    @Override
    public void saveOrUpdate(String userId ,List<Cashbook> cashbooks) {
        cashbooks.stream().forEach( cashbook -> {
            //查询该用改年月，该类型，改类别下是否存在
            CashbookStatistics queryStatistics = new CashbookStatistics();
            queryStatistics.setCreateUserid(userId);
            queryStatistics.setSyear(DateUtil.year(cashbook.getRecordTime()));
            queryStatistics.setSmonth(DateUtil.month(cashbook.getRecordTime()) + 1);
            queryStatistics.setCashType(cashbook.getCashType());
            queryStatistics.setDictId(cashbook.getDictId());
            CashbookStatistics oldStatistics = statisticsMapper.selectOne(new QueryWrapper<>(queryStatistics));
            if(oldStatistics != null){
                //更新，累加当天金额
                oldStatistics.setAmount(oldStatistics.getAmount() + cashbook.getAmount());
                statisticsMapper.updateById(oldStatistics);
            }else {
                //新增
                CashbookStatistics statistics = initCashbookStatistics(userId , cashbook);
                statisticsMapper.insert(statistics);
            }
        });
    }

    //按月统计用户账目数据
    @Async
    @Override
    public Future<CashbookShouZhiDto> statisticsByMonth(String userId, String startTime, String endTime) {
        LogUtil.info("tongji statisticsByMonth...");
        startTime = startTime.replace("-" ,"");
        endTime = endTime.replace("-" ,"");
        int startYear = Integer.parseInt(startTime.substring(0,4));
        int startMonth = Integer.parseInt(startTime.substring(4,6));
        int endYear = Integer.parseInt(endTime.substring(0,4));
        int endMonth = Integer.parseInt(endTime.substring(4,6));
        return new AsyncResult<>(statisticsMapper.statisticsByMonth(userId , startYear , startMonth , endYear , endMonth));
    }

    //按数据字典类别统计用户账目数据
    @Async
    @Override
    public Future<List<CashbookStatistics>> statisticsByCategory(String userId, int cashType , String startTime, String endTime) {
        LogUtil.info("tongji statisticsByCategory...");
        startTime = startTime.replace("-" ,"");
        endTime = endTime.replace("-" ,"");
        int startYear = Integer.parseInt(startTime.substring(0,4));
        int startMonth = Integer.parseInt(startTime.substring(4,6));
        int endYear = Integer.parseInt(endTime.substring(0,4));
        int endMonth = Integer.parseInt(endTime.substring(4,6));
        return new AsyncResult<>(statisticsMapper.statisticsByCategory(userId , cashType , startYear , startMonth , endYear , endMonth));
    }
}

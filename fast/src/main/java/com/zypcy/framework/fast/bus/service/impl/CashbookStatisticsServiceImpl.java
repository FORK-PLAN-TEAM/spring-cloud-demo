package com.zypcy.framework.fast.bus.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zypcy.framework.fast.bus.entity.Cashbook;
import com.zypcy.framework.fast.bus.entity.CashbookStatistics;
import com.zypcy.framework.fast.bus.mapper.CashbookStatisticsMapper;
import com.zypcy.framework.fast.bus.service.ICashbookStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zypcy.framework.fast.common.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 定时任务统计用户不同月份-不同类型-不同类别数据 服务实现类
 * </p>
 *
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
}

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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

/**
 * 定时任务统计用户不同月份-不同类型-不同类别数据 服务实现类
 *
 * @author zhuyu
 * @since 2019-10-09
 */
@Service
public class CashbookStatisticsServiceImpl extends ServiceImpl<CashbookStatisticsMapper, CashbookStatistics> implements ICashbookStatisticsService {

    @Autowired
    private CashbookStatisticsMapper statisticsMapper;

    @Override
    public void testTaskSaveData(String userId, String yesterDay) {
        int syear = Integer.parseInt(yesterDay.substring(0, 4));
        int smonth = Integer.parseInt(yesterDay.substring(4, 6));

        CashbookStatistics statistics = new CashbookStatistics();
        statistics.setSid(IdWorker.getFullDateId());
        statistics.setCreateUserid(userId);
        statistics.setSyear(syear);
        statistics.setSmonth(smonth);
        statistics.setCashType("0");
        statistics.setDictId("0");
        statistics.setCashCategory("test task");
        statistics.setAmount(0.00);
        statisticsMapper.insert(statistics);
    }

    private CashbookStatistics initCashbookStatistics(String userId, Cashbook cashbook) {
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
    public void saveOrUpdate(String userId, List<Cashbook> cashbooks) {
        cashbooks.stream().forEach(cashbook -> {
            //查询该用该年月，该类型，改类别下是否存在
            CashbookStatistics oldStatistics = getStatisticsExists(userId, cashbook);
            if (oldStatistics != null) {
                //更新，累加当天金额
                oldStatistics.setAmount(oldStatistics.getAmount() + cashbook.getAmount());
                statisticsMapper.updateById(oldStatistics);
            } else {
                //新增
                CashbookStatistics statistics = initCashbookStatistics(userId, cashbook);
                statisticsMapper.insert(statistics);
            }
        });
    }

    /**
     * 查询该用户该年月，该类型，改类别下是否存在数据
     *
     * @param userId
     * @param cashbook
     * @return
     */
    private CashbookStatistics getStatisticsExists(String userId, Cashbook cashbook) {
        CashbookStatistics queryStatistics = new CashbookStatistics();
        queryStatistics.setCreateUserid(userId);
        queryStatistics.setSyear(DateUtil.year(cashbook.getRecordTime()));
        queryStatistics.setSmonth(DateUtil.month(cashbook.getRecordTime()) + 1);
        queryStatistics.setCashType(cashbook.getCashType());
        queryStatistics.setDictId(cashbook.getDictId());
        return statisticsMapper.selectOne(new QueryWrapper<>(queryStatistics));
    }

    //按月统计用户账目数据
    @Async
    @Override
    public Future<List<CashbookStatistics>> statisticsByMonth(String userId, String startTime, String endTime) {
        startTime = startTime.replace("-", "");
        endTime = endTime.replace("-", "");
        int startYear = Integer.parseInt(startTime.substring(0, 4));
        int startMonth = Integer.parseInt(startTime.substring(4, 6));
        int endYear = Integer.parseInt(endTime.substring(0, 4));
        int endMonth = Integer.parseInt(endTime.substring(4, 6));
        return new AsyncResult<>(statisticsMapper.statisticsByMonth(userId, startYear, startMonth, endYear, endMonth));
    }

    //按数据字典类别统计用户账目数据
    @Async
    @Override
    public Future<List<CashbookStatistics>> statisticsByCategory(String userId, String startTime, String endTime) {
        startTime = startTime.replace("-", "");
        endTime = endTime.replace("-", "");
        int startYear = Integer.parseInt(startTime.substring(0, 4));
        int startMonth = Integer.parseInt(startTime.substring(4, 6));
        int endYear = Integer.parseInt(endTime.substring(0, 4));
        int endMonth = Integer.parseInt(endTime.substring(4, 6));
        return new AsyncResult<>(statisticsMapper.statisticsByCategory(userId, startYear, startMonth, endYear, endMonth));
    }


    @Async
    @Override
    public void addByCashbook(Cashbook cashbook) {
        //查询该用户该年月，该类型，改类别下是否存在
        CashbookStatistics oldStatistics = getStatisticsExists(cashbook.getCreateUserid(), cashbook);
        if (oldStatistics != null) {
            //新增，累加当天金额
            oldStatistics.setAmount(oldStatistics.getAmount() + cashbook.getAmount());
            statisticsMapper.updateById(oldStatistics);
        } else {
            //新增
            CashbookStatistics statistics = initCashbookStatistics(cashbook.getCreateUserid(), cashbook);
            statisticsMapper.insert(statistics);
        }
    }

    @Async
    @Override
    public void updateByCashbook(Cashbook oldCashbook, Cashbook cashbook) {
        cashbook.setCreateUserid(oldCashbook.getCreateUserid());
        //比较修改后的数据是否一致，不一致则减去之前数据，新增之后数据，一致则只修改金额
        String oldTime = DateUtil.format(oldCashbook.getRecordTime(), "yyyyMMdd");
        String nowTime = DateUtil.format(cashbook.getRecordTime(), "yyyyMMdd");
        if (oldCashbook.getCashType().equals(cashbook.getCashType())
                && oldCashbook.getDictId().equals(cashbook.getDictId())
                && timeIsEqual(oldTime, nowTime)) {
            //时间与类型相等，则说明在原有数据上修改金额
            CashbookStatistics oldStatistics = getStatisticsExists(cashbook.getCreateUserid(), cashbook);
            double amount = oldStatistics.getAmount() + cashbook.getAmount() - oldCashbook.getAmount();
            oldStatistics.setAmount(amount);
            statisticsMapper.updateById(oldStatistics);
        } else {
            //减去之前数据，新增之后数据
            CashbookStatistics oldStatistics = getStatisticsExists(oldCashbook.getCreateUserid(), oldCashbook);
            double amount = oldStatistics.getAmount() - oldCashbook.getAmount();
            oldStatistics.setAmount(amount);
            statisticsMapper.updateById(oldStatistics);

            addByCashbook(cashbook);
        }
    }

    /**
     * 判断开始时间与结束时间是否相等，时间格式：yyyyMMdd
     *
     * @param oldTime
     * @param nowTime
     * @return
     */
    private boolean timeIsEqual(String oldTime, String nowTime) {
        int startYear = Integer.parseInt(oldTime.substring(0, 4));
        int startMonth = Integer.parseInt(oldTime.substring(4, 6));
        int endYear = Integer.parseInt(nowTime.substring(0, 4));
        int endMonth = Integer.parseInt(nowTime.substring(4, 6));
        if (startYear == endYear && startMonth == endMonth) {
            return true;
        } else {
            return false;
        }
    }

    @Async
    @Override
    public void deleteByCashbook(Cashbook cashbook) {
        CashbookStatistics oldStatistics = getStatisticsExists(cashbook.getCreateUserid(), cashbook);
        oldStatistics.setAmount(oldStatistics.getAmount() - cashbook.getAmount());
        statisticsMapper.updateById(oldStatistics);
    }
}

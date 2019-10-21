package com.zypcy.framework.fast.bus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zypcy.framework.fast.bus.entity.Cashbook;
import com.zypcy.framework.fast.bus.mapper.CashbookMapper;
import com.zypcy.framework.fast.bus.service.ICashbookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zypcy.framework.fast.common.config.ContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 记账本 服务实现类
 * </p>
 *
 * @author zhuyu
 * @since 2019-07-29
 */
@Service
public class CashbookServiceImpl extends ServiceImpl<CashbookMapper, Cashbook> implements ICashbookService {

    @Autowired CashbookMapper cashbookMapper;

    /**
     * 统计当日金额
     * @param userId
     * @return
     */
    @Override
    public Map<String,String> getCurrentDayAmount(String userId) {
        return cashbookMapper.getCurrentDayAmount(userId);
    }

    /**
     * 统计一段时间内的金额
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Map<String, String> getTimeSlotAmount(String userId, String startTime, String endTime) {
        return cashbookMapper.getTimeSlotAmount(userId , startTime , endTime);
    }

    @Override
    public int add(Cashbook cashbook) {
        cashbook.setIsdel(false);
        cashbook.setCreateUserid(ContextHolder.getUserId());
        cashbook.setCreateUsername(ContextHolder.getUserName());
        cashbook.setCreateTime(LocalDateTime.now());
        cashbook.setUpdateUserid(ContextHolder.getUserId());
        cashbook.setUpdateUsername(ContextHolder.getUserName());
        cashbook.setUpdateTime(LocalDateTime.now());
        return cashbookMapper.insert(cashbook);
    }

    @Override
    public boolean deleteBatchById(String cashId) {
        return cashbookMapper.deleteById(cashId) > 0 ? true : false;
    }


    @Override
    public IPage<Cashbook> pageList(String cashType, String startTime, String endTime, String dictId, int pageIndex, int pageSize) {
        String userId = ContextHolder.getUserId();//只能看自己的信息
        Page<Cashbook> page = new Page<>(pageIndex , pageSize);
        Cashbook cashbook = new Cashbook();
        if(!"admin".equals(userId)){
            cashbook.setCreateUserid(userId);
        }
        cashbook.setIsdel(false);
        if(!StringUtils.isEmpty(cashType)){
            cashbook.setCashType(cashType);
        }
        if(!StringUtils.isEmpty(dictId)){
            cashbook.setDictId(dictId);
        }
        //按时间查询
        QueryWrapper<Cashbook> wrapper = new QueryWrapper<>(cashbook);
        wrapper.orderByDesc("record_time");
        if(!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)){
            wrapper.ge("record_time" , startTime);
            wrapper.le("record_time" , endTime + " 23:59:59");
        }else if(!StringUtils.isEmpty(startTime)){
            wrapper.between("record_time", startTime , startTime + " 23:59:59");
        }
        return cashbookMapper.selectPage(page , wrapper);
    }

    /**
     * 统计每人，某天、不同类型、不同类别的账目数据
     * @param createUserId
     * @param sTime
     * @return
     */
    @Override
    public List<Cashbook> statisticsDayAmount(String createUserId, String sTime) {
        return cashbookMapper.statisticsDayAmount(createUserId , sTime);
    }
}

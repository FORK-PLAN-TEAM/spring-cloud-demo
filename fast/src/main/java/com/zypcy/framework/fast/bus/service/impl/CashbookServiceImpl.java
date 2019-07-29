package com.zypcy.framework.fast.bus.service.impl;

import com.zypcy.framework.fast.bus.entity.Cashbook;
import com.zypcy.framework.fast.bus.mapper.CashbookMapper;
import com.zypcy.framework.fast.bus.service.ICashbookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zypcy.framework.fast.common.config.ContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Override
    public double getTotalAmount(String userId) {
        return cashbookMapper.getTotalAmount(userId);
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
}

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


    @Override
    public IPage<Cashbook> pageList(String startTime, String endTime, int pageIndex, int pageSize) {
        String userId = ContextHolder.getUserId();//只能看自己的信息
        Page<Cashbook> page = new Page<>(pageIndex , pageSize);
        Cashbook cashbook = new Cashbook();
        cashbook.setIsdel(false);
        if(!"admin".equals(userId)){
            cashbook.setCreateUserid(userId);
        }
        //按时间查询
        QueryWrapper<Cashbook> wrapper = new QueryWrapper<>(cashbook);
        wrapper.orderByDesc("record_time");
        if(!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)){
            wrapper.between("record_time", startTime , endTime);
        }else if(!StringUtils.isEmpty(startTime)){
            wrapper.between("record_time", startTime , startTime + " 23:59:59");
        }
        return cashbookMapper.selectPage(page , wrapper);
    }
}

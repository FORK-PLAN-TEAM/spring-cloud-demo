package com.zypcy.framework.fast.bus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zypcy.framework.fast.bus.entity.Cashbook;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 记账本 服务类
 * </p>
 *
 * @author zhuyu
 * @since 2019-07-29
 */
public interface ICashbookService extends IService<Cashbook> {

    int add(Cashbook cashbook);

    boolean deleteBatchById(String cashId);

    double getTotalAmount(String userId);

    IPage<Cashbook> pageList(String startTime , String endTime,int pageIndex , int pageSize);
}

package com.zypcy.framework.fast.bus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zypcy.framework.fast.bus.entity.Cashbook;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

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

    /**
     * 统计当日金额
     * @param userId
     * @return
     */
    Map<String,String> getCurrentDayAmount(String userId);

    /**
     * 统计一段时间内的金额
     * @param userId
     * @return
     */
    Map<String,String> getTimeSlotAmount(String userId , String startTime, String endTime);

    /**
     * 翻页查询
     * @param cashType  账目类型
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param pageIndex 页码
     * @param pageSize  每页数据量
     * @return
     */
    IPage<Cashbook> pageList(String cashType, String startTime , String endTime,int pageIndex , int pageSize);
}

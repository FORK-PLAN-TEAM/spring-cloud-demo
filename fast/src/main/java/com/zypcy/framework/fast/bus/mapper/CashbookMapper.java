package com.zypcy.framework.fast.bus.mapper;

import com.zypcy.framework.fast.bus.entity.Cashbook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 记账本 Mapper 接口
 * </p>
 *
 * @author zhuyu
 * @since 2019-07-29
 */
public interface CashbookMapper extends BaseMapper<Cashbook> {

    @Update("update bus_cashbook set isdel=1 where cash_id=#{cashId}")
    int deleteById(@Param("cashId") String cashId);
}

package com.zypcy.framework.fast.bus.mapper;

import com.zypcy.framework.fast.bus.entity.Cashbook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 记账本 Mapper 接口
 * </p>
 *
 * @author zhuyu
 * @since 2019-07-29
 */
public interface CashbookMapper extends BaseMapper<Cashbook> {

    //统计当日金额
    //@Select("select IFNULL(sum(amount),0) as amount from bus_cashbook where create_userid=#{createUserid} and isdel=0 ")
    @Select("select \n" +
            "(select IFNULL(sum(amount),0) from bus_cashbook where to_days(record_time) = to_days(now()) and create_userid=#{createUserid} and cash_type=0 and isdel=0) as zhichu ,\n" +
            "(select IFNULL(sum(amount),0) from bus_cashbook where to_days(record_time) = to_days(now()) and create_userid=#{createUserid} and cash_type=1 and isdel=0) as shouru\n")
    Map<String,String> getCurrentDayAmount(@Param("createUserid")String createUserid);

    //统计一段时间内的金额
    @Select("select \n" +
            "(select IFNULL(sum(amount),0) from bus_cashbook where DATE_FORMAT( record_time, '%Y%m%d' )>= DATE_FORMAT(#{startTime} , '%Y%m%d' ) and DATE_FORMAT( record_time, '%Y%m%d' )<= DATE_FORMAT(#{endTime} , '%Y%m%d' ) and create_userid=#{createUserid} and cash_type=0 and isdel=0) as zhichu ,\n" +
            "(select IFNULL(sum(amount),0) from bus_cashbook where DATE_FORMAT( record_time, '%Y%m%d' )>= DATE_FORMAT(#{startTime} , '%Y%m%d' ) and DATE_FORMAT( record_time, '%Y%m%d' )<= DATE_FORMAT(#{endTime} , '%Y%m%d' ) and create_userid=#{createUserid} and cash_type=1 and isdel=0) as shouru\n")
    Map<String, String> getTimeSlotAmount(@Param("createUserid")String createUserid, @Param("startTime")String startTime, @Param("endTime")String endTime);

    //统计每人某天、不同类型、不同类别的账目数据
    @Select("select record_time,cash_type,cash_category,dict_id,sum(amount) as amount from bus_cashbook where create_userid=#{createUserId} and date_format(record_time,'%Y%m%d')=#{sTime}\n" +
            "group by date_format(record_time,'%Y%m%d'),dict_id,cash_type")
    List<Cashbook> statisticsDayAmount(String createUserId , String sTime);

    @Update("update bus_cashbook set isdel=1 where cash_id=#{cashId}")
    int deleteById(@Param("cashId") String cashId);
}

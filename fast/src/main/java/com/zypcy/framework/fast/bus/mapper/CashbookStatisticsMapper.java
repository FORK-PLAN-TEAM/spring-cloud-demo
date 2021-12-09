package com.zypcy.framework.fast.bus.mapper;

import com.zypcy.framework.fast.bus.entity.CashbookStatistics;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 定时任务统计用户不同月份-不同类型-不同类别数据 Mapper 接口
 *
 * @author zhuyu
 * @since 2019-10-09
 */
public interface CashbookStatisticsMapper extends BaseMapper<CashbookStatistics> {

    //按月统计
    //@Select("select \n" +
    //        " (select IFNULL(sum(amount),0) from bus_cashbook_statistics where create_userid=#{userId} and syear>=#{startYear} and syear<=#{endYear} and smonth>=#{startMonth} and smonth<=#{endMonth} and cash_type=0) as zhichu ,\n" +
    //        " (select IFNULL(sum(amount),0) from bus_cashbook_statistics where create_userid=#{userId} and syear>=#{startYear} and syear<=#{endYear} and smonth>=#{startMonth} and smonth<=#{endMonth} and cash_type=1) as shouru")
    @Select("(select syear,smonth,cash_type, IFNULL(sum(amount),0) as amount from bus_cashbook_statistics where create_userid=#{userId} and syear>=#{startYear} and syear<=#{endYear} and smonth>=#{startMonth} and smonth<=#{endMonth} and cash_type=0 group by syear , smonth)\n" +
            "UNION \n" +
            "(select syear,smonth,cash_type, IFNULL(sum(amount),0) as amount from bus_cashbook_statistics where create_userid=#{userId} and syear>=#{startYear} and syear<=#{endYear} and smonth>=#{startMonth} and smonth<=#{endMonth} and cash_type=1 group by syear , smonth)\n")
    List<CashbookStatistics> statisticsByMonth(@Param("userId") String userId, @Param("startYear") int startYear, @Param("startMonth") int startMonth, @Param("endYear") int endYear, @Param("endMonth") int endMonth);

    //按类别统计
    //@Select("select syear,smonth,cash_type,cash_category,sum(amount) as amount from bus_cashbook_statistics where create_userid=#{userId} and syear>=#{startYear} and syear<=#{endYear} and smonth>=#{startMonth} and smonth<=#{endMonth} and cash_type=#{cashType}\n" +
    //        "group by cash_category\n" +
    //        "order by syear , smonth asc , amount desc")
    @Select("(select syear,smonth,cash_type,cash_category,sum(amount) as amount from bus_cashbook_statistics where create_userid=#{userId} and syear>=#{startYear} and syear<=#{endYear} and smonth>=#{startMonth} and smonth<=#{endMonth} and cash_type=1\n" +
            "group by syear,smonth,cash_category)\n" +
            "union\n" +
            "(select syear,smonth,cash_type,cash_category,sum(amount) as amount from bus_cashbook_statistics where create_userid=#{userId} and syear>=#{startYear} and syear<=#{endYear} and smonth>=#{startMonth} and smonth<=#{endMonth} and cash_type=0\n" +
            "group by syear,smonth,cash_category)\n" +
            "order by syear , smonth asc , cash_type desc,amount desc")
    List<CashbookStatistics> statisticsByCategory(@Param("userId") String userId, @Param("startYear") int startYear, @Param("startMonth") int startMonth, @Param("endYear") int endYear, @Param("endMonth") int endMonth);
}

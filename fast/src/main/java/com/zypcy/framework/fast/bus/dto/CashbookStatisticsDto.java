package com.zypcy.framework.fast.bus.dto;

import com.zypcy.framework.fast.bus.entity.CashbookStatistics;
import io.swagger.annotations.ApiModel;

import java.util.List;
import java.util.Map;

@ApiModel(value = "CashbookStatisticsDto对象", description = "按月统计数据与按类别数据")
public class CashbookStatisticsDto {

    private List<CashbookStatistics> months;
    private List<CashbookStatistics> categorys;

    public List<CashbookStatistics> getMonths() {
        return months;
    }

    public void setMonths(List<CashbookStatistics> months) {
        this.months = months;
    }

    public List<CashbookStatistics> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<CashbookStatistics> categorys) {
        this.categorys = categorys;
    }
}

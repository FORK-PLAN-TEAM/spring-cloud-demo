package com.zypcy.framework.fast.bus.dto;

import com.zypcy.framework.fast.bus.entity.CashbookStatistics;
import io.swagger.annotations.ApiModel;

import java.util.List;
import java.util.Map;

@ApiModel(value = "CashbookStatisticsDto对象", description = "按月统计数据与按类别数据")
public class CashbookStatisticsDto {

    private CashbookShouZhiDto months;
    private List<CashbookStatistics> categoryShouru;
    private List<CashbookStatistics> categoryZhichu;

    public CashbookShouZhiDto getMonths() {
        return months;
    }

    public void setMonths(CashbookShouZhiDto months) {
        this.months = months;
    }

    public List<CashbookStatistics> getCategoryShouru() {
        return categoryShouru;
    }

    public void setCategoryShouru(List<CashbookStatistics> categoryShouru) {
        this.categoryShouru = categoryShouru;
    }

    public List<CashbookStatistics> getCategoryZhichu() {
        return categoryZhichu;
    }

    public void setCategoryZhichu(List<CashbookStatistics> categoryZhichu) {
        this.categoryZhichu = categoryZhichu;
    }
}

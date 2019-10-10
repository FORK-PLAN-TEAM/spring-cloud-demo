package com.zypcy.framework.fast.bus.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "记账本收支对象", description = "收入与支出金额")
public class CashbookShouZhiDto {

    private double shouru;
    private double zhichu;

    public double getShouru() {
        return shouru;
    }

    public void setShouru(double shouru) {
        this.shouru = shouru;
    }

    public double getZhichu() {
        return zhichu;
    }

    public void setZhichu(double zhichu) {
        this.zhichu = zhichu;
    }
}

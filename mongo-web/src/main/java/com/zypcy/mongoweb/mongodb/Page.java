package com.zypcy.mongoweb.mongodb;

/**
 * @Author zhuyu
 * @Time 2020-06-09 10:09
 * @Description 翻页对象
 */
public class Page {
    //每页显示条数
    private Long pageSize = 20L;

    //当前页数
    private Long pageIndex = 1L;

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }
}

package com.zypcy.framework.fast.common.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 此类暂时未使用，返回的是MybatisPlus的IPage类，再由table.js控件转成需要的格式
 *
 * @param <T>
 */
@ApiModel(value = "PageListModel", description = "Layui-Table控件需要的数据结构")
public class PageModel<T> implements Serializable {

    /**
     * 使用了Mybatis-Plus的自动翻页插件，需要把翻页结果转换为前端table控件需要的数据格式
     * com.baomidou.mybatisplus.extension.plugins.pagination.Page.class
     * new Page(long current, long size)  ----> current对应PageIndex , size对应PageSize
     * Mybatis-Plus翻页查询后的返回结果：
     *  {"current":1,"pages":5,"records":[ {"menuId":"2","menuName":"菜单管理","orderNum":10011,"parentId":"1"}],"searchCount":true,"size":2,"total":9}
     * 参数讲解：
     *  current : 当前第几页
     *  pages  :  总共有几页
     *  records : 数据集合
     *  size   : PageSize 每页显示多少条数据
     *  total  : 总数据条数
     */
/**
 @ApiModelProperty(value = "成功为0")
 private int code;

 @ApiModelProperty(value = "提示")
 private String msg;

 @ApiModelProperty(value = "数据量总数")
 private Long count;

 @ApiModelProperty(value = "列表数据集合")
 private List<T> data;


  * 把Mybatis-Plus翻页插件查询的数据转换为，layui-table控件需要的格式
  * @param pages
 * @param <T>
 * @return public static <T> PageModel<T> convert(IPage<T> pages){
PageModel model = new PageModel();
model.setCode(0); //非0则失败
model.setMsg("查询成功");
model.setCount(pages.getTotal());
model.setData(pages.getRecords());
return model;
}*/
}

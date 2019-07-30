package com.zypcy.framework.fast.sys.service;

import com.zypcy.framework.fast.sys.dto.ZySysTree;
import com.zypcy.framework.fast.sys.entity.ZySysDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
public interface IZySysDictService extends IService<ZySysDict> {

    ZySysDict getById(String id);

    /**
     * 获取数据字典树
     * @return
     */
    List<ZySysTree> getDictTrees(String parentId);

    /**
     * 根据parentId 获取数据字典
     * @param parentId
     * @return
     */
    List<ZySysDict> getByParentId(String parentId);

    List<ZySysDict> getByType(String type);

    /**
     * 删除子字典
     * @param parentId
     * @return
     */
    int delChild(String parentId);
}

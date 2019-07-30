package com.zypcy.framework.fast.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zypcy.framework.fast.sys.dto.ZySysTree;
import com.zypcy.framework.fast.sys.entity.ZySysDict;
import com.zypcy.framework.fast.sys.mapper.ZySysDictMapper;
import com.zypcy.framework.fast.sys.service.IZySysDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@Service
public class ZySysDictServiceImpl extends ServiceImpl<ZySysDictMapper, ZySysDict> implements IZySysDictService {

    @Autowired ZySysDictMapper dictMapper;

    @Override
    public ZySysDict getById(String id) {
        ZySysDict dict = new ZySysDict();
        dict.setId(id);
        dict.setIsdel(false);
        return dictMapper.selectOne(new QueryWrapper<>(dict));
    }

    @Override
    public List<ZySysTree> getDictTrees(String parentId) {
        return dictMapper.getDictTrees(parentId);
    }

    @Override
    public List<ZySysDict> getByParentId(String parentId) {
        ZySysDict dict = new ZySysDict();
        dict.setParentId(parentId);
        dict.setIsdel(false);
        QueryWrapper wrapper = new QueryWrapper<>(dict);
        wrapper.orderByAsc("id");
        return dictMapper.selectList(wrapper);
    }

    @Override
    public List<ZySysDict> getByType(String type) {
        ZySysDict dict = new ZySysDict();
        dict.setType(type);
        dict.setIsdel(false);
        QueryWrapper wrapper = new QueryWrapper<>(dict);
        wrapper.orderByAsc("id");
        return dictMapper.selectList(wrapper);
    }


    @Override
    public int delChild(String parentId) {
        return dictMapper.delChild(parentId);
    }
}

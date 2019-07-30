package com.zypcy.framework.fast.sys.mapper;

import com.zypcy.framework.fast.sys.dto.ZySysTree;
import com.zypcy.framework.fast.sys.entity.ZySysDict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
public interface ZySysDictMapper extends BaseMapper<ZySysDict> {

    @Update("update zy_sys_dict set isdel=1 where parent_id=#{parentId} ")
    int delChild(@Param("parentId") String parentId);

    @Select(" select id , parent_id  as p_id, name,type as extend_data from zy_sys_dict where parent_id=#{parentId} and isdel=0 ")
    List<ZySysTree> getDictTrees(@Param("parentId") String parentId);
}

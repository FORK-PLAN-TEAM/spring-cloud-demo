package com.zypcy.framework.fast.sys.mapper;

import com.zypcy.framework.fast.sys.entity.ZySysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
public interface ZySysRoleMapper extends BaseMapper<ZySysRole> {

    @Update("update zy_sys_role set isdel=1 where role_id=#{roleId}")
    int deleteOrgById(@Param("roleId") String roleId);
}

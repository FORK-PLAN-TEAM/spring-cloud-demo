package com.zypcy.framework.fast.sys.mapper;

import com.zypcy.framework.fast.sys.entity.ZySysRole;
import com.zypcy.framework.fast.sys.entity.ZySysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户角色 Mapper 接口
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
public interface ZySysUserRoleMapper extends BaseMapper<ZySysUserRole> {

    @Select("select role.role_id , role.role_name , role.role_code from zy_sys_user_role user_role \n" +
            "INNER JOIN zy_sys_role role on user_role.role_id = role.role_id and role.isdel=0\n" +
            "where user_role.user_id=#{userId}")
    List<ZySysRole> getUserRoles(@Param("userId") String userId);

}

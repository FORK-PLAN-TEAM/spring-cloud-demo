package com.zypcy.framework.fast.sys.mapper;

import com.zypcy.framework.fast.sys.entity.ZySysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
public interface ZySysUserMapper extends BaseMapper<ZySysUser> {


    @Update("update zy_sys_user set isdel=1 where user_id=#{userId}")
    int deleteById(@Param("userId") String userId);
}

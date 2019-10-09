package com.zypcy.framework.fast.sys.mapper;

import com.zypcy.framework.fast.sys.entity.ZySysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    @Select(" select count(user_id) from zy_sys_user where user_account=#{userAccount} ")
    int existsUserAccount(@Param("userAccount") String userAccount);

    @Select("select user_id from zy_sys_user where isdel = 0 and state=0")
    List<String> listAllUserId();
}

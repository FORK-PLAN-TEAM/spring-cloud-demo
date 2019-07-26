package com.zypcy.framework.fast.sys.service;

import com.zypcy.framework.fast.sys.entity.ZySysRole;
import com.zypcy.framework.fast.sys.entity.ZySysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色 服务类
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
public interface IZySysUserRoleService extends IService<ZySysUserRole> {

    /**
     * 获取用户创建的角色信息
     * @param userId
     * @return
     */
    List<ZySysRole> getUserCreateRoles(String userId);

    /**
     * 获取用户已保存的角色信息
     * @param userId
     * @return
     */
    List<ZySysRole> getUserSaveRoles(String userId);
}

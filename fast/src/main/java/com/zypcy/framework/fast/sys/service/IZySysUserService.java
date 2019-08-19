package com.zypcy.framework.fast.sys.service;

import com.zypcy.framework.fast.sys.entity.ZySysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
public interface IZySysUserService extends IService<ZySysUser> {

    /**
     * 新增用户信息
     * @param user
     * @return
     */
    int add(ZySysUser user);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    boolean update(ZySysUser user);

    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @return
     */
    boolean updatePwd(String oldPwd ,String newPwd);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    boolean deleteById(String userId);

    /**
     * 该帐号是否存在
     * @return
     */
    boolean existsUserAccount(String userAccount);
}

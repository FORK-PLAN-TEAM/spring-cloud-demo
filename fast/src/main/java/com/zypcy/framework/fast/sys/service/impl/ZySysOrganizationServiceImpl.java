package com.zypcy.framework.fast.sys.service.impl;

import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.common.util.IdWorker;
import com.zypcy.framework.fast.sys.entity.ZySysOrganization;
import com.zypcy.framework.fast.sys.entity.ZySysUser;
import com.zypcy.framework.fast.sys.mapper.ZySysOrganizationMapper;
import com.zypcy.framework.fast.sys.service.IZySysOrganizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 组织机构 服务实现类
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
@Service
public class ZySysOrganizationServiceImpl extends ServiceImpl<ZySysOrganizationMapper, ZySysOrganization> implements IZySysOrganizationService {

    @Autowired private ZySysOrganizationMapper organizationMapper;

    @Override
    public int add(ZySysOrganization organization) {
        ZySysUser user = ContextHolder.getUserInfo();
        organization.setOrgId(IdWorker.getDateId());
        /*organization.setCreateUserid(user.getCreateUserid());
        organization.setCreateUsername(user.getCreateUsername());
        organization.setCreateTime(LocalDateTime.now());
        organization.setUpdateUserid(user.getUpdateUserid());
        organization.setCreateUsername(user.getUpdateUsername());
        organization.setUpdateTime(LocalDateTime.now());*/
        return organizationMapper.insert(organization);
    }
}

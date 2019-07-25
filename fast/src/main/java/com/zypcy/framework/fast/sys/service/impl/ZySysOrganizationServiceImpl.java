package com.zypcy.framework.fast.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zypcy.framework.fast.common.config.ContextHolder;
import com.zypcy.framework.fast.sys.entity.ZySysOrganization;
import com.zypcy.framework.fast.sys.dto.ZySysTree;
import com.zypcy.framework.fast.sys.mapper.ZySysOrganizationMapper;
import com.zypcy.framework.fast.sys.service.IZySysOrganizationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private ZySysOrganizationMapper organizationMapper;

    @Override
    public int add(ZySysOrganization organization) {
        //organization.setOrgId(IdWorker.getId());
        organization.setIsdel(false);
        organization.setCreateUserid(ContextHolder.getUserId());
        organization.setCreateUsername(ContextHolder.getUserName());
        organization.setCreateTime(LocalDateTime.now());
        organization.setUpdateUserid(ContextHolder.getUserId());
        organization.setUpdateUsername(ContextHolder.getUserName());
        organization.setUpdateTime(LocalDateTime.now());
        return organizationMapper.insert(organization);
    }

    @Override
    public ZySysOrganization getOrganizationById(String orgId) {
        ZySysOrganization organization = new ZySysOrganization();
        organization.setIsdel(false);
        organization.setOrgId(orgId);
        return organizationMapper.selectOne(new QueryWrapper<>(organization));
    }

    @Override
    public boolean deleteOrgById(String orgId) {
        return organizationMapper.deleteOrgById(orgId) > 0 ? true : false;
    }

    @Override
    public List<ZySysTree> getOrgTrees() {
        return organizationMapper.getOrgTrees();
    }
}

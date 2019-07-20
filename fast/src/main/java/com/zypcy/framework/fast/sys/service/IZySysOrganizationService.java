package com.zypcy.framework.fast.sys.service;

import com.zypcy.framework.fast.sys.entity.ZySysOrganization;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zypcy.framework.fast.sys.entity.ZySysTree;

import java.util.List;

/**
 * <p>
 * 组织机构 服务类
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
public interface IZySysOrganizationService extends IService<ZySysOrganization> {

    int add(ZySysOrganization organization);

    ZySysOrganization getOrganizationById(String orgId);

    boolean deleteOrgById(String orgId);

    /**
     * 获取组织机构树
     * @return
     */
    List<ZySysTree> getOrgTrees();
}

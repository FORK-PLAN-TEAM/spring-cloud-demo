package com.zypcy.framework.fast.sys.service;

import com.zypcy.framework.fast.sys.entity.ZySysOrganization;
import com.baomidou.mybatisplus.extension.service.IService;

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

}

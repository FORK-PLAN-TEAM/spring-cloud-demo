package com.zypcy.framework.fast.sys.mapper;

import com.zypcy.framework.fast.sys.entity.ZySysOrganization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zypcy.framework.fast.sys.dto.ZySysTree;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 组织机构 Mapper 接口
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
public interface ZySysOrganizationMapper extends BaseMapper<ZySysOrganization> {

    @Select("select org_id as id , parent_id as p_id , org_name as name from zy_sys_organization where isdel=0")
    List<ZySysTree> getOrgTrees();

    @Update("update zy_sys_organization set isdel=1 where org_id=#{orgId}")
    int deleteOrgById(@Param("orgId") String orgId);
}

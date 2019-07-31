package com.zypcy.framework.fast.sys.mapper;

import com.zypcy.framework.fast.sys.entity.ZySysAttach;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 附件表，同一用户上传多个附件请用attach_no Mapper 接口
 * </p>
 *
 * @author zhuyu
 * @since 2019-07-31
 */
public interface ZySysAttachMapper extends BaseMapper<ZySysAttach> {

    @Select("select * from zy_sys_attach limit 0,20;")
    List<ZySysAttach> listAttachTop20();
}

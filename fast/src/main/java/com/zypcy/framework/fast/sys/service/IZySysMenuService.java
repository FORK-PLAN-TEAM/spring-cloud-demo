package com.zypcy.framework.fast.sys.service;

import com.zypcy.framework.fast.sys.entity.ZySysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zypcy.framework.fast.sys.dto.ZySysTree;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author zhuyu
 * @since 2019-06-14
 */
public interface IZySysMenuService extends IService<ZySysMenu> {

    /**
     * 获取所有菜单树
     * @return
     */
    List<ZySysTree> getMenuTrees();

}

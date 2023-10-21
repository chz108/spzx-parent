package com.atguigu.spzx.mapper;

import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/18
 */
public interface SysMenuMapper {
    List<SysMenu> findAllNodes();

    void save(SysMenu sysMenu);

    void update(SysMenu sysMenu);

    void remove(Long id);

    SysMenu findParent(Long parentId);
}

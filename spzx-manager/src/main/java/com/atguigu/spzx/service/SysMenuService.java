package com.atguigu.spzx.service;

import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/18
 */
public interface SysMenuService {
    List<SysMenu> findNodes();

    void save(SysMenu sysMenu);

    void updateById(SysMenu sysMenu);

    void removeById(Long id);

}

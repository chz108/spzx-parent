package com.atguigu.spzx.service.impl;

import com.atguigu.spzx.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.service.SysMenuService;
import com.atguigu.spzx.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaozhen
 * @date 2023/10/18
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuService sysMenuService;

    // 查询所有的菜单,回显角色id对应的拥有可以操作的菜单
    @Override
    public Map<String, Object> findMenu(Integer roleId) {
        // 1. 查询素有菜单
        List<SysMenu> allNodes = sysMenuService.findNodes();
        Map<String, Object> map = new HashMap<>();
        map.put("sysMenuList", allNodes);
        // 2. 回显该角色拥有的菜单
        List<Long> roleMenuIds = sysRoleMenuMapper.findMenuByRoleId(roleId);
        map.put("roleMenuIds", roleMenuIds);
        return map;
    }

    //
    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {

        List<Map<String, Number>> menuIdList = assginMenuDto.getMenuIdList();
        // 1. 判断菜单id是否为空
        if (menuIdList != null && menuIdList.size() > 0) {
            // 2. 通过id删除原来所拥有的菜单
            sysRoleMenuMapper.deleteByRoleId(assginMenuDto.getRoleId());
            // 3. 添加菜单
            sysRoleMenuMapper.doAssign(assginMenuDto);
        }

    }
}

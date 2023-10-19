package com.atguigu.spzx.service.impl;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.mapper.SysMenuMapper;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.service.SysMenuService;
import com.atguigu.spzx.utils.MenuHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/18
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        // 查询所有的菜单节点
        List<SysMenu> allNodesList = sysMenuMapper.findAllNodes();
        // 判断菜单节点是否为空
        if(CollectionUtils.isEmpty(allNodesList)) return null;
        // 使用工具类将所有父节点和节点按照规定封装起来
        List <SysMenu> list = MenuHelper.buildTree(allNodesList);
        return list;
    }

    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.save(sysMenu);
    }

    @Override
    public void updateById(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
    }

    @Override
    public void removeById(Long id) {
        // 删除菜单,如果这个菜单有下一级就不允许被删除
        // 1. 先向数据库中查询这这个菜单是否有下一级(id是否被其他的节点使用)
        List<SysMenu> allNodes = sysMenuMapper.findAllNodes();
        allNodes.stream().forEach(sysMenu -> {
            if (sysMenu.getParentId().equals(id)) {
                throw new GuiguException(ResultCodeEnum.NODE_ERROR);
            }
        });
        // 2. 如果这个节点下面没有子节点的话就允许删除
        sysMenuMapper.remove(id);
    }
}

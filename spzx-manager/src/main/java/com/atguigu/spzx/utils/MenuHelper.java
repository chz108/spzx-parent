package com.atguigu.spzx.utils;

import com.atguigu.spzx.model.entity.system.SysMenu;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/18
 */
public class MenuHelper {

    public static  List <SysMenu> buildTree(List<SysMenu> allNodesList) {
        List<SysMenu> tree = new ArrayList<>();
        // 遍历所有节点,然后找到根节点
        for (SysMenu sysMenu : allNodesList) {
            if (sysMenu.getParentId() == 0) {
                tree.add(findChild(sysMenu, allNodesList));
            }
        }
        return tree;
    }

    private static SysMenu findChild(SysMenu sysMenu, List<SysMenu> allNodesList) {
        // 初始化
        sysMenu.setChildren(new ArrayList<>());
        // 遍历所有节点,然后找到子节点
        for (SysMenu menu : allNodesList) {
            if (sysMenu.getId().equals(menu.getParentId())) {
                // 使用递归的方式将这些节点都找到
                sysMenu.getChildren().add(findChild(menu, allNodesList));
            }
        }
        return sysMenu;
    }
}

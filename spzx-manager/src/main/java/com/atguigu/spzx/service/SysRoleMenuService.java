package com.atguigu.spzx.service;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;

import java.util.Map;

/**
 * @author xiaozhen
 * @date 2023/10/18
 */
public interface SysRoleMenuService {
    Map<String, Object> findMenu(Integer roleId);

    void doAssign(AssginMenuDto assginMenuDto);

}

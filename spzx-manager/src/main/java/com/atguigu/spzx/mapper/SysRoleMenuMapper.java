package com.atguigu.spzx.mapper;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/18
 */
public interface SysRoleMenuMapper {
    List<Long> findMenuByRoleId(Integer roleId);

    void deleteByRoleId(Long roleId);

    void doAssign(AssginMenuDto assginMenuDto);

    void updateISHalf(Long id);
}

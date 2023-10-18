package com.atguigu.spzx.mapper;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiaozhen
 * @date 2023/10/17
 */
public interface SysRoleUserMapper {
    void saveSysRoleUser(@Param("userId") Long userId, @Param("roleId") Long roleId);

    void delete(Long userId);
}

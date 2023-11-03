package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.h5.UserCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/11/2
 */
public interface UserCollectMapper {
    UserCollect findCollect(@Param("userId") Long userId,@Param("skuId") Long skuId);

    Integer collect(Long userId, Long skuId);

    List<UserCollect> queryCollectPage(Long userId);
}

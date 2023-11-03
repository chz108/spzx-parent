package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.h5.UserCollect;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;
import com.github.pagehelper.PageInfo;

/**
 * @author xiaozhen
 * @date 2023/10/31
 */
public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo(String token);

    Boolean isCollect(Long skuId);

    Boolean collect(Long skuId);

    PageInfo<UserCollect> findUserCollectPage(Long page, Long limit);
}

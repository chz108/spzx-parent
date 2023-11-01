package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserInfo;

/**
 * @author xiaozhen
 * @date 2023/10/31
 */
public interface UserInfoMapper {
    UserInfo findUserByUserName(String username);

    void register(UserInfo user);
}

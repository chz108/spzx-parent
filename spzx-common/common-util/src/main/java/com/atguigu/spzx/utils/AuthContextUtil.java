package com.atguigu.spzx.utils;

import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.entity.user.UserInfo;

/**
 * @author xiaozhen
 * @date 2023/10/15
 */
public class AuthContextUtil {
    private static ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    public static SysUser get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

    private static ThreadLocal<UserInfo> threadLocalUserInfo = new ThreadLocal<>();

    public static void setUserInfo(UserInfo userInfo) {
        threadLocalUserInfo.set(userInfo);
    }
    public static UserInfo getUserInfo() {
        return threadLocalUserInfo.get();
    }
    public static void removeUserInfo() {
        threadLocalUserInfo.remove();
    }
}

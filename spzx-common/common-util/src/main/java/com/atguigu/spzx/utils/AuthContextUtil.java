package com.atguigu.spzx.utils;

import com.atguigu.spzx.model.entity.system.SysUser;

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
}

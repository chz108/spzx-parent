package com.atguigu.spzx.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author xiaozhen
 * @date 2023/10/31
 */
public class UserLoginAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String userInfoStr = redisTemplate.opsForValue().get(token);
        UserInfo userInfo = JSON.parseObject(userInfoStr, UserInfo.class);
        AuthContextUtil.setUserInfo(userInfo);
        return true;
    }

}

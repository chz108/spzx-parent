package com.atguigu.spzx.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaozhen
 * @date 2023/10/15
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是预检请求的话就直接放行
        if (("OPTIONS").equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        // 判断token是否存在
        String token = request.getHeader("token");
        if (StrUtil.isEmpty(token)) {
            responseNoLoginInfo(response);
            return false;
        }

        // redis中获取到用户信息
        String redisUserInfo = redisTemplate.opsForValue().get("user:login:" + token);
        // 判断是否为空
        if (StrUtil.isEmpty(redisUserInfo)) {
            responseNoLoginInfo(response);
            return false;
        }
        // 将json数据装成对象
        SysUser sysUser = JSON.parseObject(redisUserInfo, SysUser.class);
        // 将这个sysUser对象放入到ThreadLocal中
        AuthContextUtil.set(sysUser);

        // 每次点击一个功能就将用户过期时间
        redisTemplate.expire("user:login:" + token, 30, TimeUnit.MINUTES);

        return true;
    }

    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContextUtil.remove();
    }
}

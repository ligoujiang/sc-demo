package com.pxy.user.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxy.user.utils.R;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //退出登录的处理
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //清空redis中的token
        stringRedisTemplate.delete("user:login");
        //System.out.println("退出："+authentication.getPrincipal());

        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String json=objectMapper.writeValueAsString(R.success(200,"退出成功"));
        response.getWriter().write(json);
    }
}

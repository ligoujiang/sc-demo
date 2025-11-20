package com.pxy.user.handler;

import cn.hutool.jwt.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxy.user.utils.R;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //登录成功的处理
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //生成token
        String key="sdfjlkelwkqnflkwnqeflkl";
        Map<String,Object> map=new HashMap<>();
        map.put("user",authentication.getPrincipal());
        String token=JWTUtil.createToken(map,key.getBytes());
        System.out.println(token);

//        System.out.println(authentication.getPrincipal());
//        User user=(User)authentication.getPrincipal();
//        System.out.println(user.getId());
        //存入redis
        stringRedisTemplate.opsForValue().set("user:login",token);

        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String json=objectMapper.writeValueAsString(R.success(200,"登录成功",token));
        response.getWriter().write(json);
    }
}

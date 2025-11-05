package com.pxy.user.filter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.pxy.user.entity.User;
import com.pxy.user.utils.Result;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {
    private String key="sdfjlkelwkqnflkwnqeflkl";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //登录接口不需要token
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        if(requestURI.equals("/login")){ //如果是登录地址，直接放行
            filterChain.doFilter(request,response);
        }else{
            String token=request.getHeader("Authorization"); //获取请求头中的token
            if(!StringUtils.hasText(token)){ //当token为空
                response.getWriter()
                        .write(JSONUtil.toJsonStr(Result.error(901,"请求token为空")));
            }else if(!JWTUtil.verify(token,key.getBytes())){ //校验token是否合法
                response.getWriter()
                        .write(JSONUtil.toJsonStr(Result.error(902,"请求token不合法")));
            }else{
                JSONObject payload=JWTUtil.parseToken(token).getPayloads();
                String userJson=payload.get("user").toString();
                User  user=JSONUtil.toBean(userJson,User.class);
                Integer userid=user.getId();
                //获取redis中的token，进行比较
                String redisToken=stringRedisTemplate.opsForValue().get("user:login");
                if(!token.equals(redisToken)){
                    response.getWriter()
                            .write(JSONUtil.toJsonStr(Result.error(903,"请求token错误")));
                }else{
                    //在Sscurity句柄放置认证对象，这样Security在执行后面的Filter的时候，才知道是认证过的
                    UsernamePasswordAuthenticationToken authentication
                            =new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    //token验证通过，放行
                    filterChain.doFilter(request,response);
                }
            }
        }
    }
}

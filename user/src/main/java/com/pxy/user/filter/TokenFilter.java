package com.pxy.user.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.pxy.user.domain.po.Menu;
import com.pxy.user.domain.po.User;
import com.pxy.user.domain.dto.UserDetailsDTO;
import com.pxy.user.utils.R;
import com.pxy.user.utils.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TokenFilter extends OncePerRequestFilter {
    private String key="sdfjlkelwkqnflkwnqeflkl";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 定义不需要 token 验证的路径
    private static final String[] EXCLUDE_PATHS = {
            "/doc.html",
            "/webjars/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/v2/api-docs",
            "/v2/api-docs/**",
            "/swagger-ui/**",
            "/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/user/register",
            "/user/login",
            "/user/hello3"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String requestURI = request.getRequestURI();
        log.info("请求的资源路径:{}",requestURI);
        //登录接口不需要token
        // 如果是排除路径，直接放行
        if (isExcludePath(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        if(requestURI.equals("/login")){ //如果是登录地址，直接放行
            filterChain.doFilter(request,response);
        }else{
            String token=request.getHeader("Authorization"); //获取请求头中的token
            if(!StringUtils.hasText(token)){ //当token为空
                response.getWriter()
                        .write(JSONUtil.toJsonStr(R.error(901,"请求token为空")));
            }else if(!JWTUtil.verify(token,key.getBytes())){ //校验token是否合法
                response.getWriter()
                        .write(JSONUtil.toJsonStr(R.error(902,"请求token不合法")));
            }else{
                // 无状态
                // 解析token
                JSONObject payload=JWTUtil.parseToken(token).getPayloads();
                String userJson=payload.get("user").toString();
                // 解析JSON
                JSONObject jsonObject = JSONUtil.parseObj(userJson);
                // 获取user对象
                User user = JSONUtil.toBean(jsonObject.getJSONObject("user"), User.class);
                // 获取权限列表
                List<Menu> authoritieList = JSONUtil.toList(
                        jsonObject.getJSONArray("AuthoritieList"), Menu.class);
                // 创建UserDetailsVO
                UserDetailsDTO userDetailsVO = new UserDetailsDTO(user, authoritieList);
                // 存入thread local
                UserContext.setUserId(userDetailsVO.getUser().getId());

                //TODO 从redis中获取，用于判断是否主动失效
                if(stringRedisTemplate.hasKey("blacklist:"+token)){
                    response.getWriter()
                            .write(JSONUtil.toJsonStr(R.error(903,"请求token已失效",null)));
                }else{
                    // 放置认证对象，这样Security在执行后面的Filter的时候，才知道是认证过的
                    UsernamePasswordAuthenticationToken authentication //userDetailsVO.getAuthorities()
                            =new UsernamePasswordAuthenticationToken(userDetailsVO,null, userDetailsVO.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    // token验证通过，放行
                    filterChain.doFilter(request,response);
                }
            }
        }
    }
    // 判断路径是否在放行列表中（简单匹配前缀）
    private boolean isExcludePath(String path) {
        for (String excludePath : EXCLUDE_PATHS) {
            if (excludePath.endsWith("/**")) {
                String prefix = excludePath.substring(0, excludePath.length() - 3);
                if (path.startsWith(prefix)) {
                    return true;
                }
            } else if (path.equals(excludePath)) {
                return true;
            }
        }
        return false;
    }
}



//有状态
//                //解析token获取用户id
//                JSONObject payload=JWTUtil.parseToken(token).getPayloads();
//                Long userId = Convert.toLong(payload);
//                //存入thread local
//                UserContext.setUserId(userId);
//                //获取redis中的token，进行比较
//                String redisToken=stringRedisTemplate.opsForValue().get("user:login:"+token);
//                if(redisToken==null){
//                    response.getWriter()
//                            .write(JSONUtil.toJsonStr(R.error(903,"请求token不一致")));
//                }else{
//                    //刷新token时效
//                    stringRedisTemplate.expire("user:login:"+token,30, TimeUnit.MINUTES);
//                    // 从redis获取用户和权限信息
//                    // 解析JSON
//                    JSONObject jsonObject = JSONUtil.parseObj(redisToken);
//                    // 获取user对象
//                    User user = JSONUtil.toBean(jsonObject.getJSONObject("user"), User.class);
//                    // 获取权限列表
//                    List<Menu> authoritieList = JSONUtil.toList(
//                        jsonObject.getJSONArray("AuthoritieList"), Menu.class);
//                    // 创建UserDetailsVO
//                    UserDetailsDTO userDetailsDTO = new UserDetailsDTO(user, authoritieList);
//
//                    //在Sscurity句柄放置认证对象，这样Security在执行后面的Filter的时候，才知道是认证过的
//                    UsernamePasswordAuthenticationToken authentication //userDetailsVO.getAuthorities()
//                            =new UsernamePasswordAuthenticationToken(userDetailsDTO,null, userDetailsDTO.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                    //token验证通过，放行
//                    filterChain.doFilter(request,response);
//                }

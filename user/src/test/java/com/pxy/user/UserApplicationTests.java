package com.pxy.user;

import cn.hutool.jwt.JWTUtil;
import com.pxy.user.entity.User;
import com.pxy.user.mapper.MenuMapper;
import com.pxy.user.mapper.RoleMapper;
import com.pxy.user.mapper.UserMapper;
import com.pxy.user.mapper.admin.AdminMapper;
import com.pxy.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class UserApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AdminMapper adminMapper;

    @Test
    void contextLoads() {
//        User user=userMapper.getUserByName("lisi");
//        System.out.println(user);
        //userMapper.setPassword(1,"123456");

        //userService.setPassword(1,"123456");
        //System.out.println(userMapper.getMenus(4));
        //System.out.println(roleMapper.getRoles(4));

//        String key="sdfjlkelwkqnflkwnqeflkl";
//        Map<String,Object> map=new HashMap<>();
//        map.put("id",1);
//        String token= JWTUtil.createToken(map,key.getBytes());
//        System.out.println(token);

        //stringRedisTemplate.opsForValue().set("key","value");
        //System.out.println(menuMapper.getMenus(4));

        System.out.println(adminMapper.Users());
    }

}

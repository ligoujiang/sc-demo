package com.pxy.user;

import com.pxy.user.entity.User;
import com.pxy.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        User user=userMapper.getUserByName("lisi");
        System.out.println(user);
    }

}

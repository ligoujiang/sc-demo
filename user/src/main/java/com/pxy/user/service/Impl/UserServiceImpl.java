package com.pxy.user.service.Impl;

import com.pxy.user.mapper.UserMapper;
import com.pxy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.pxy.user.entity.User user=userMapper.getUserByName(username);
        if(user==null){
            System.out.println(username);
            throw new UsernameNotFoundException(username);
        }

        return user;  //实体类实现了UserDetails接口，所以可以直接返回该类
        
        // 使用密码编码器对密码进行编码
        //String encodedPassword = passwordEncoder.encode("123456");

//        UserDetails  userDetails = User.builder()
//                .username(user.getName())
//                .password(user.getPassword())
//                .authorities(AuthorityUtils.NO_AUTHORITIES) //权限为空
//                .build();
        //return userDetails;
    }
}

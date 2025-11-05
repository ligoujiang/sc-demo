package com.pxy.user.service.Impl;

import com.pxy.user.domain.po.Menu;
import com.pxy.user.mapper.MenuMapper;
import com.pxy.user.mapper.RoleMapper;
import com.pxy.user.mapper.UserMapper;
import com.pxy.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;



    /**
     * 认证 校验
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //认证  查询用户是否存在数据
        com.pxy.user.entity.User user=userMapper.getUserByName(username);
        if(user==null){
            System.out.println(username);
            throw new UsernameNotFoundException(username+"账号不存在");
        }
        //校验 查询用户有哪些权限
        //List<Role> roleList=roleMapper.getRoles(user.getId());
        List<Menu> menuList=menuMapper.getMenus(user.getId());
        //将权限信息放入用户对象
        user.setAuthoritieList(menuList);
        return user;  //实体类实现了UserDetails接口，所以可以直接返回该类
    }

    /**
     * 通过id修改用户密码
     * @param id
     * @param password
     */
    public void setPassword(Integer id,String password){
        String enPassword=passwordEncoder.encode(password);
        userMapper.setPassword(id, enPassword);
    }
}

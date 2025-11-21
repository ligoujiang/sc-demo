package com.pxy.user.service.Impl;

import com.pxy.user.domain.po.Menu;
import com.pxy.user.domain.po.User;
import com.pxy.user.domain.vo.UserDetailsVO;
import com.pxy.user.mapper.MenuMapper;
import com.pxy.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 查询用户和权限
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //认证-查询用户是否存在
        User user=userMapper.getUserByName(username);
        if(user==null){
            System.out.println(username);
            throw new UsernameNotFoundException(username+"账号不存在");
        }
        //校验-查询用户有哪些权限
        List<Menu> menuList=menuMapper.getMenus(user.getId());
        //将权限信息放入用户对象
        return new UserDetailsVO(user,menuList); //后续便于获取认证信息
    }
}

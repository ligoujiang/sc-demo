package com.pxy.user.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.pxy.user.domain.dto.UserDTO;
import com.pxy.user.domain.po.Menu;
import com.pxy.user.domain.po.User;
import com.pxy.user.domain.vo.UserDetailsVO;
import com.pxy.user.domain.vo.UserVO;
import com.pxy.user.mapper.MenuMapper;
import com.pxy.user.mapper.UserMapper;
import com.pxy.user.service.UserService;
import com.pxy.user.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户认证和校验
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //认证  查询用户是否存在数据
        User user=userMapper.getUserByName(username);
        if(user==null){
            System.out.println(username);
            throw new UsernameNotFoundException(username+"账号不存在");
        }
        //校验 查询用户有哪些权限
        List<Menu> menuList=menuMapper.getMenus(user.getId());
        //将权限信息放入用户对象
        return new UserDetailsVO(user,menuList);  //实体类实现了UserDetails接口，所以可以直接返回该类
    }

    /**
     * 通过id修改用户密码
     * @param
     * @param password
     */
    public void setPassword(String password){
        Long userId= UserContext.getUserId();
        User user=new User();
        user.setId(userId);
        user.setPassword(passwordEncoder.encode(password));
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }

    /**
     * 根据id获取用户信息
     * @return
     */
    @Override
    public UserVO getUser() {
        Long userId= UserContext.getUserId();
        return userMapper.getUser(userId);
    }

    /**
     * 用户注册
     * @param userDTO
     */
    @Override
    public void register(UserDTO userDTO) {
        User user= BeanUtil.copyProperties(userDTO,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleId("1");
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
    }
}

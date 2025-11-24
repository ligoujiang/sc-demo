package com.pxy.user.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.pxy.user.domain.dto.UserDTO;
import com.pxy.user.domain.dto.UserDetailsDTO;
import com.pxy.user.domain.po.Menu;
import com.pxy.user.domain.po.User;
import com.pxy.user.domain.vo.UserVO;
import com.pxy.user.mapper.UserMapper;
import com.pxy.user.service.UserService;
import com.pxy.user.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户认证和校验
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //认证  查询用户是否存在数据
//        User user=userMapper.getUserByName(username);
//        if(user==null){
//            System.out.println(username);
//            throw new UsernameNotFoundException(username+"账号不存在");
//        }
//        //校验 查询用户有哪些权限
//        List<Menu> menuList=menuMapper.getMenus(user.getId());
//        //将权限信息放入用户对象
//        return new UserDetailsVO(user,menuList);  //实体类实现了UserDetails接口，所以可以直接返回该类
//    }

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

    /**
     * 用户登录
     * @param userDTO
     */
    @Override
    public String login(UserDTO userDTO) {
        UsernamePasswordAuthenticationToken  authenticationToken=new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
            //调用这个方法会执行UserDetailServiceImpl类中的loadUserByUsername方法
            Authentication authentication = authenticationManager.authenticate(authenticationToken); //认证异常已由全局异常处理类处理
            //将认证信息存入Security上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //生成token
            return createToken(authentication);
    }

    //随机生成token
    public String createToken(Authentication authentication){
        Object principal = authentication.getPrincipal();
        String key="sdfjlkelwkqnflkwnqeflkl";
        Map<String,Object> map=new HashMap<>();
        map.put("user",principal);
        map.put("exp", (System.currentTimeMillis() + 3600000) / 1000); // 1小时
        String token= JWTUtil.createToken(map,key.getBytes());
        System.out.println(token);
        return token;
    }

//    //有状态
//    public String createToken(Authentication authentication){
//        User user=null;
//        Object principal = authentication.getPrincipal();
//        //获取用户信息
//        if (principal instanceof UserDetailsDTO customUserDetails) {
//            // 5. 获取UserDetailsDTO类中的自定义属性
//            user = customUserDetails.getUser();
//        }
//        String key="sdfjlkelwkqnflkwnqeflkl";
//        Map<String,Object> map=new HashMap<>();
//        map.put("userId",user.getId());
//        String token= JWTUtil.createToken(map,key.getBytes());
//        System.out.println(token);
//        //存入redis
//        String json = JSONUtil.toJsonStr(principal);
//        stringRedisTemplate.opsForValue().set("user:login:"+token, json,30, TimeUnit.MINUTES);
//        return token;
//    }
}

package com.pxy.user;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pxy.user.domain.po.User;
import com.pxy.user.mapper.MenuMapper;
import com.pxy.user.mapper.UserMapper;
import com.pxy.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

class person{
    Integer id;
    String name;
    public person(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

@Slf4j
@SpringBootTest
class UserApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


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

        //System.out.println(adminMapper.Users());
    }

    private static final Object lock = new Object();
    ReentrantLock Lock = new ReentrantLock();

    @Test
    void test(){
        Thread thread=new Thread(this::test1,"t1");
        thread.start();
        Thread thread2=new Thread(()->{
            test1();
        },"t2");
        thread2.start();
    }
    void test1(){
        Lock.lock();
        log.info(Thread.currentThread().getName()+":test1");
        Lock.unlock();
    }

    @Test
    void test2() throws InterruptedException {
//        String str1= new String("hello");
//        String str2= new String("hello");
//        System.out.println(System.identityHashCode(str1));
//        System.out.println(System.identityHashCode(str2));
//        if(str1.equals(str2)){
//            log.info("true");
//        }
//        if (str1==str2){
//            log.info("true");
//        }
//        person p1=new person(1,"test");
//        person p2=new person(1,"test");
//        if (p1.equals(p2)){
//            log.info("true");
//        }
//        if (p1==p2){
//            log.info("true");
//        }

//        Page<User> page=new Page<>(1,2);
//        userMapper.selectPage(page,null);
//        System.out.println(page.getRecords());

        // 创建 JWT Token
        String key = "your-secret-key";


        String token = JWT.create()
                .setPayload("userId", "12345")
                .setPayload("username", "admin")
                .setExpiresAt(new Date(System.currentTimeMillis() + 1000 * 2 )) // 1000 * 60 * 60 * 24 24小时过期
                .setKey(key.getBytes())
                .sign();
        System.out.println("JWT Token: " + token);

        // 验证 Token
        // 等待3秒后再验证
        Thread.sleep(3000);
        boolean verify = JWTUtil.verify(token, key.getBytes());
        System.out.println("Token 验证结果: " + verify);

        // 2. 验证时间
        try{
            JWTValidator.of(token).validateDate(DateUtil.date());
        }catch (Exception e){
            System.out.println("已过期");
        }

//        // 手动验证过期时间
//        JWT jwt = JWTUtil.parseToken(token);
//        Object exp = jwt.getPayload("exp");
//        if (exp != null) {
//            long expireTime = Long.parseLong(exp.toString()) * 1000L; // 转为毫秒
//            long currentTime = System.currentTimeMillis();
//            boolean isExpired = currentTime > expireTime;
//
//            System.out.println("过期时间: " + new Date(expireTime));
//            System.out.println("当前时间: " + new Date(currentTime));
//            System.out.println("是否已过期: " + isExpired);
//        }
    }
}

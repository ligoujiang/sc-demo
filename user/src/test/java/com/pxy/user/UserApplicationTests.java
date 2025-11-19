package com.pxy.user;

import com.pxy.user.mapper.MenuMapper;
import com.pxy.user.mapper.RoleMapper;
import com.pxy.user.mapper.UserMapper;
import com.pxy.user.mapper.admin.AdminMapper;
import com.pxy.user.service.UserService;
import io.lettuce.core.output.ScoredValueScanOutput;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
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
    void test2(){
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
        person p1=new person(1,"test");
        person p2=new person(1,"test");
        if (p1.equals(p2)){
            log.info("true");
        }
        if (p1==p2){
            log.info("true");
        }
    }
}

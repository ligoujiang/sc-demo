package com.pxy.user.controller;

import com.pxy.user.mapper.admin.AdminMapper;
import com.pxy.user.utils.LoginInfoUtil;
import com.pxy.user.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

//@RestController
@Controller
@RequestMapping
public class UserController {

    @Autowired
    private AdminMapper adminMapper;

    //所以用户都可访问
    @ResponseBody
    @GetMapping("/hello3")
    public String hello3(){
        return "hello3";
    }

    @PreAuthorize("hasAuthority('user:123')")
    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

//    //登录页地址
//    @GetMapping("/toLogin")
//    public String Login(){
//        return "login";
//    }

    //登录成功后跳转的地址
    @ResponseBody
    //@RequestMapping("/welcome")
    @GetMapping("/welcome")
    public Object welcome(Principal principal){
        return LoginInfoUtil.getCurrentLoginUser();
        //return SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //直接获取登录账号的信息
        //return principal;
    }

    @PreAuthorize("hasAuthority('user:info')")
    @ResponseBody
    @GetMapping("/getUserInfo")
    public Object getUserInfo(){
        return Result.success(200,"获取成功",LoginInfoUtil.getCurrentLoginUser());
    }
}

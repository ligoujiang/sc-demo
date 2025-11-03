package com.pxy.user.controller;

import com.pxy.user.utils.LoginInfoUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

//@RestController
@Controller
@RequestMapping
public class UserController {


    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    //登录页地址
    @GetMapping("/toLogin")
    public String Login(){
        return "login";
    }

    //登录成功后跳转的地址
    @ResponseBody
    //@RequestMapping("/welcome")
    @GetMapping("/welcome")
    public Object welcome(Principal principal){
        return LoginInfoUtil.getCurrentLoginUser();
        //return SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //直接获取登录账号的信息
        //return principal;
    }
}

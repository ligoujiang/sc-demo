package com.pxy.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pxy.user.domain.dto.PageDTO;
import com.pxy.user.domain.dto.UserDTO;
import com.pxy.user.domain.po.User;
import com.pxy.user.domain.vo.PageVO;
import com.pxy.user.domain.vo.UserVO;
import com.pxy.user.mapper.UserMapper;
import com.pxy.user.service.UserService;
import com.pxy.user.utils.LoginInfoUtil;
import com.pxy.user.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper  userMapper;

    //所有用户都可访问
    @GetMapping("/hello3")
    public String hello3(){
        return "hello3";
    }

    @GetMapping("/hello2")
    public String hello2(){
        return "hello2";
    }

    @PreAuthorize("hasAuthority('user:123')")
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

//    @PreAuthorize("hasAuthority('user:info')")
//    @GetMapping("/getUserInfo")
//    public Object getUserInfo(){
//        return R.success(200,"获取成功",LoginInfoUtil.getCurrentLoginUser());
//    }


    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public R register(@RequestBody UserDTO userDTO){
        userService.register(userDTO);
        return R.success(200,"注册成功");
    }

    @Operation(summary = "根据id获取用户信息")
    @PreAuthorize("hasAuthority('system:user:query')")
    @GetMapping()
    public R<UserVO> getUser(){
        //return Result.success(200,"获取成功",LoginInfoUtil.getCurrentLoginUser());
        UserVO userVO = userService.getUser();
        return R.success(200,"获取成功",userVO);
    }

    @Operation(summary = "修改密码")
    @PreAuthorize("hasAuthority('system:user:update')")
    @PostMapping("/{password}")
    public R setPassword(@PathVariable String password){
        userService.setPassword(password);
        return R.success(200,"修改成功");
    }

    @Operation(summary = "分页查询用户信息")
    @PreAuthorize("hasAuthority('system:user:page')")
    @GetMapping("/page")
    public R<PageVO> getUsers(PageDTO pageDTO){
        Page<User> page=new Page<>(pageDTO.getPage(),pageDTO.getSize());
        userMapper.selectPage(page,null);
        PageVO pageVO=new PageVO();
        pageVO.setTotal(page.getTotal());
        pageVO.setPages(page.getPages());
        pageVO.setRecords(Collections.singletonList(page.getRecords()));
        return R.success(200,"查询成功",pageVO);
    }
}

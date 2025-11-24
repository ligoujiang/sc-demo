package com.pxy.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pxy.user.domain.dto.UserDTO;
import com.pxy.user.domain.po.User;
import com.pxy.user.domain.vo.PageVO;
import com.pxy.user.domain.vo.UserVO;
import com.pxy.user.mapper.UserMapper;
import com.pxy.user.service.UserService;
import com.pxy.user.utils.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public R register(@RequestBody UserDTO userDTO){
        userService.register(userDTO);
        return R.success(200,"注册成功");
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public R login(@RequestBody UserDTO userDTO){
        String token=userService.login(userDTO);
        return R.success(200,"登录成功",token);
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


    @Operation(summary = "修改用户角色")
    @PreAuthorize("hasAuthority('system:user:update')")
    @PostMapping("/role/{roleId}")
    public R updateRole(@PathVariable Integer roleId){
        //userService.updateRole(roleId);
        return R.success(200,"修改成功");
    }

    @Operation(summary = "分页查询用户信息")
    @PreAuthorize("hasAuthority('system:user:page')")
    @GetMapping("/page")
    public R<PageVO> getUsers(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "5") Integer size,
                              @RequestParam(required = false, defaultValue = "asc") String sortOrder){
        Page<User> p=new Page<>(page,size);
        userMapper.selectPage(p,null);
        PageVO pageVO=new PageVO();
        pageVO.setTotal(p.getTotal());
        pageVO.setPages(p.getPages());
        pageVO.setRecords(p.getRecords());
        return R.success(200,"查询成功",pageVO);
    }
}

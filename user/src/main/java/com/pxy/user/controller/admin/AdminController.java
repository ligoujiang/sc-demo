package com.pxy.user.controller.admin;

import com.pxy.user.domain.po.User;
import com.pxy.user.mapper.admin.AdminMapper;
import com.pxy.user.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminMapper adminMapper;

    @GetMapping("/users")
    public Result Users(){

        List<User> userList=adminMapper.Users();

        return Result.success(200,"查询所有用户",userList);
    }
}

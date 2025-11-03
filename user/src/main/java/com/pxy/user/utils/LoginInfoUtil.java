package com.pxy.user.utils;

import com.pxy.user.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginInfoUtil {
    public static User getCurrentLoginUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

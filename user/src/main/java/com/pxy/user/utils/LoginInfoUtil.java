package com.pxy.user.utils;

import com.pxy.user.domain.vo.UserDetailsVO;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginInfoUtil {
    public static UserDetailsVO getCurrentLoginUser(){
        return (UserDetailsVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

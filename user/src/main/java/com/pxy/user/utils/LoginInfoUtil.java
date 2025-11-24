package com.pxy.user.utils;

import com.pxy.user.domain.dto.UserDetailsDTO;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginInfoUtil {
    public static UserDetailsDTO getCurrentLoginUser(){
        return (UserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

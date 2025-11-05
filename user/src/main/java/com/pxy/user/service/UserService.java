package com.pxy.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public void setPassword(Integer id,String password);
}

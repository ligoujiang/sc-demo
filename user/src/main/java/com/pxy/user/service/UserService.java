package com.pxy.user.service;

import com.pxy.user.domain.dto.UserDTO;
import com.pxy.user.domain.vo.UserVO;
import org.springframework.security.core.userdetails.UserDetailsService;

//extends UserDetailsService
public interface UserService{
    void setPassword(String password);

    UserVO getUser();

    void register(UserDTO userDTO);

    String login(UserDTO userDTO);
}

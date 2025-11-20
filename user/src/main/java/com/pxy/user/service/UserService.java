package com.pxy.user.service;

import com.pxy.user.domain.dto.UserDTO;
import com.pxy.user.domain.vo.UserVO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void setPassword(String password);

    UserVO getUser();

    void register(UserDTO userDTO);
}

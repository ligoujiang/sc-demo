package com.pxy.user.mapper;

import com.pxy.user.entity.User;

public interface UserMapper {
    User getUserByName(String username);
}

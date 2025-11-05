package com.pxy.user.mapper;

import com.pxy.user.domain.po.Role;

import java.util.List;

public interface RoleMapper {

    /**
     * 通过用户id查询角色
     * @param userId
     * @return
     */
    public List<Role> getRoles(Integer userId);
}

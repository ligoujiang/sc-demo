package com.pxy.user.mapper;

import com.pxy.user.domain.po.Menu;

import java.util.List;

public interface MenuMapper {
    /**
     * 通过用户id查询权限符
     * @param userId
     * @return
     */
    public List<Menu> getMenus(Long userId);
}

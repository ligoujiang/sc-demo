package com.pxy.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pxy.user.domain.po.Menu;
import com.pxy.user.domain.po.User;
import com.pxy.user.domain.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    /**
     * 通过用户名查找信息
     * @param username
     * @return
     */
    User getUserByName(String username);

    /**
     * 修改用户密码
     * @param id
     * @param password
     */
    void setPassword(@Param("id") Long id, @Param("password") String password);

    /**
     * 通过用户id查询权限表
     * @param id
     * @return
     */
    List<Menu> getMenus(Long id);

    UserVO getUser(Long userId);
}

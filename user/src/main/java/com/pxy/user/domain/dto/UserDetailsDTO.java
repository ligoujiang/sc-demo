package com.pxy.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pxy.user.domain.po.Menu;
import com.pxy.user.domain.po.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
//@Builder！！！  当实体类中存在不是数据表中的字段时，mybits映射会出错
public class UserDetailsDTO implements UserDetails {
    private User user;
    // 关键：表示该字段不映射到数据库
    @JsonIgnore
    private List<Menu> AuthoritieList; // 角色/权限符列表

    public UserDetailsDTO(User user, List<Menu> AuthoritieList) {
        this.user = user;
        this.AuthoritieList = Objects.requireNonNullElseGet(AuthoritieList, ArrayList::new);
    }
    //-----------继承并实现UserDetails接口----------//

    //此方法当需要权限验证时，会自动调用
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //返回权限列表
        Collection<GrantedAuthority>  authorities = new ArrayList<>();
        for (Menu authoritie : AuthoritieList) {
            authorities.add(new SimpleGrantedAuthority(authoritie.getCode()));
        }
        return authorities; //返回用户的权限（角色或者code）
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { //账号是否过期 0为过期 1为正常
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //用户凭证已过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //用户帐号已被锁定
        return true;
    }

    @Override
    public boolean isEnabled() { //用户已失效
        return true;
    }
}


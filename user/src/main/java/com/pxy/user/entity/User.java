package com.pxy.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
public class User implements UserDetails {
    Integer id;
    String name;
    @JsonIgnore
    String password;
    String status;
    String roleId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updateDate;

    //-----------实现UserDetails接口----------//

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //返回权限列表
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() { //账号是否过期 0为过期 1为正常
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

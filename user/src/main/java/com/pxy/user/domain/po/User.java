package com.pxy.user.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    @Schema(description = "用户id",example = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @Schema(description = "用户名称",example = "admin")
    private String name;
    @JsonIgnore
    @Schema(description = "用户密码",example = "123456")
    private String password;
    @Schema(description = "用户角色",example = "管理员")
    private String roleId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}

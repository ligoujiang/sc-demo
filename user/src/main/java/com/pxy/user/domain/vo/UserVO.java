package com.pxy.user.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserVO {
    @Schema(description = "用户id",example = "1")
    private Long id;
    @Schema(description = "用户名称",example = "admin")
    private String name;
    @Schema(description = "用户角色",example = "管理员")
    private String roleId;
}

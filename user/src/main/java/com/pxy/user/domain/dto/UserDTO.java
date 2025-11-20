package com.pxy.user.domain.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDTO {
    @Schema(description = "用户名称",example = "admin")
    private String name;
    @Schema(description = "用户密码",example = "123456")
    private String password;
}

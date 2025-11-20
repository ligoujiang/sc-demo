package com.pxy.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PageDTO {
    @Schema(description = "页码",example = "1")
    private Integer page;
    @Schema(description = "页大小",example = "5")
    private Integer size;
    private String sort;
    private String order;
}

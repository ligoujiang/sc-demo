package com.pxy.user.domain.po;

import lombok.Data;

@Data
public class Menu {
    private Integer id;
    private String name;
    private String code;
    private String url;
    private String type;
    private Integer parentId;
    private Integer orderNo;
    private String icon;
    private String component;
}

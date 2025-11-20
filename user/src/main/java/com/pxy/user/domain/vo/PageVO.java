package com.pxy.user.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVO{
    private long total; //总大小
    private long size;
    private long pages; //总页数
    private List<Object> records; //数据
}

package com.pxy.user.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVO<T>{
    private long total; //总大小
    private long pages; //总页数
    private List<T> records; //数据
}

package com.pxy.user.utils;

import lombok.Data;

/**
 * 后端统一返回结果
 * @param <T>
 */
@Data
public class R<T> {
    private Integer code; //响应码
    private String message; //信息
    private T data;    //数据


    public static <T> R<T> success(String message){
        R<T> r = new R<T>();
        r.code = 200;
        r.message = message;
        r.data = null;
        return r;
    }

    public static <T> R<T> success(Integer code, String message){
        R<T> r = new R<T>();
        r.code = code;
        r.message = message;
        r.data = null;
        return r;
    }

    public static <T> R<T> success(Integer code, String message, T data){
        R<T> r = new R<T>();
        r.code = code;
        r.message = message;
        r.data = data;
        return r;
    }

    public static <T> R<T> error(String message){
        R<T> r = new R<T>();
        r.code = 500;
        r.message = message;
        r.data = null;
        return r;
    }
    public static <T> R<T> error(Integer code, String message){
        R<T> r = new R<T>();
        r.code = code;
        r.message = message;
        r.data = null;
        return r;
    }
    public static <T> R<T> error(Integer code, String message, T data){
        R<T> r = new R<T>();
        r.code = code;
        r.message = message;
        r.data = data;
        return r;
    }
}

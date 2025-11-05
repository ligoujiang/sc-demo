package com.pxy.user.utils;

import lombok.Data;

@Data
public class Result {
    private Integer code;
    private String message;
    private Object data;


    public static Result success(String message){
        Result result = new Result();
        result.code = 200;
        result.message = message;
        result.data = null;
        return result;
    }

    public static Result success(Integer code,String message){
        Result result = new Result();
        result.code = code;
        result.message = message;
        result.data = null;
        return result;
    }

    public static Result success(Integer code, String message, Object data){
        Result result = new Result();
        result.code = code;
        result.message = message;
        result.data = data;
        return result;
    }

    public static Result error(String message){
        Result result = new Result();
        result.code = 500;
        result.message = message;
        result.data = null;
        return result;
    }
    public static Result error(Integer code,String message){
        Result result = new Result();
        result.code = code;
        result.message = message;
        result.data = null;
        return result;
    }
    public static Result error(Integer code, String message, Object data){
        Result result = new Result();
        result.code = code;
        result.message = message;
        result.data = data;
        return result;
    }
}

package com.pxy.user.global;

import com.pxy.user.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.core.AuthenticationException;

//全局异常处理类
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //处理用户认证失败
    @ExceptionHandler(AuthenticationException.class)
    public R<String> handleAuthenticationException(AuthenticationException e) {
        String errorMessage = e.getMessage();
        log.error("认证失败: {}", errorMessage);
        return R.error(401, errorMessage, null);
    }
//    private String getAuthenticationErrorMessage(AuthenticationException e) {
//        if (e instanceof BadCredentialsException) {
//            return "登录失败，账号或密码错误";
//        } else if (e instanceof LockedException) {
//            return "账号已被锁定，请联系管理员";
//        } else if (e instanceof DisabledException) {
//            return "账号已被禁用，请联系管理员";
//        } else if (e instanceof AccountExpiredException) {
//            return "账号已过期，请联系管理员";
//        } else {
//            return "认证失败，请检查登录信息";
//        }
//    }
}

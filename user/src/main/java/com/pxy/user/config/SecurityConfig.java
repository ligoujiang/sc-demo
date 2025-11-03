package com.pxy.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用BCrypt密码编码器
        return new BCryptPasswordEncoder();
    }

    //配置Security框架的一些行为
    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                //自定义登录页
                .formLogin((formLogin) -> {
                    formLogin
                            .loginProcessingUrl("/login") //表单发起请求的地址
                            .loginPage("/toLogin") //登录页面的地址
                            .defaultSuccessUrl("/welcome", true); // 此用法不需要是get请求
                            //.successForwardUrl("/welcome"); //登录成功跳转的地址,默认跳转上个地址   //注意！！跳转的请求方法必须是post
                })
                .authorizeHttpRequests((authorizeHttpRequests) -> {
                    authorizeHttpRequests
                            .requestMatchers("/toLogin").permitAll()
                            .anyRequest().authenticated();//任何请求都需要认证
                })
                .build();
    }
}

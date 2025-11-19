package com.pxy.user.config;

import com.pxy.user.filter.TokenFilter;
import com.pxy.user.handler.MyAccessDeniedHandler;
import com.pxy.user.handler.MyAuthenticationFailureHandler;
import com.pxy.user.handler.MyAuthenticationSuccessHandler;
import com.pxy.user.handler.MyLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@EnableMethodSecurity //开启方法权限检查
@Configuration
//@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    MyAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    MyAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    MyLogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    MyAccessDeniedHandler accessDeniedHandler;
    @Autowired
    TokenFilter tokenFilter;

    //密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用BCrypt密码编码器
        return new BCryptPasswordEncoder();
    }

    //配置跨域
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //跨域配置
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*")); //允许任何来源
        corsConfiguration.setAllowedMethods(List.of("*")); //允许任何请求方法
        corsConfiguration.setAllowedHeaders(List.of("*")); //允许任何请求头头

        //注册跨域配置
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }


    //配置Security框架的一些行为
    @Bean
    //HttpSecurity http和CorsConfigurationSource corsConfigurationSource 属于方法参数注入
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http,CorsConfigurationSource corsConfigurationSource) throws Exception {
        return http
                //登录
                .formLogin((formLogin) -> {
                    formLogin
                            .loginProcessingUrl("/login")//前端登录的账号和密码往这个地址发送请求
                                    .successHandler(authenticationSuccessHandler) //登录成功后执行该handler
                                    .failureHandler(authenticationFailureHandler); //登录失败后执行该handler
                            //.loginPage("/toLogin") //登录页面的地址 重定向
                            //.defaultSuccessUrl("/welcome", true); // 此用法不需要是get请求
                            //.successForwardUrl("/welcome"); //登录成功跳转的地址,默认跳转上个地址   //注意！！跳转的请求方法必须是post
                })
                //退出
                .logout((logout) -> {
                    logout
                            .logoutUrl("/logout") //前端退出往这个地址发起请求
                            .logoutSuccessHandler(logoutSuccessHandler); //退出成功后执行该handler
                })
                //认证配置
                .authorizeHttpRequests((authorizeHttpRequests) -> {
                    authorizeHttpRequests
                            .requestMatchers("/toLogin","/hello3","/error").permitAll()
                            .anyRequest().authenticated();//任何请求都需要认证
                })
                //无权限
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.accessDeniedHandler(accessDeniedHandler); //权限不足的时候，执行该handler
                })

                //无session状态
                .sessionManagement((sessionManagement) -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                //关闭csrf跨站请求伪造
                .csrf(AbstractHttpConfigurer::disable) // ✅ 禁用CSRF保护
                //设置跨域（允许前端跨域访问）
                .cors((cors)->{
                    cors.configurationSource(corsConfigurationSource);
                })
                //添加过滤器
                .addFilterBefore(tokenFilter, LogoutFilter.class) //添加到退出过滤器之前，这样退出过滤器可以拿到用户信息
                .build();
    }
}

package com.zerone.scms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig2 {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.exceptionHandling().accessDeniedPage("/error/denied");   //当用户访问没有授权的资源时，会被拒绝，此处设置的是被拒绝的页面
        http.logout()
                .logoutUrl("/logout")               //退出登录（注消）的URL，此URL不用自己写控制器，由Spring Security处理
                .logoutSuccessUrl("/login_page");   //成功退出登录后返回的页面，必须是自己写的控制器或页面，且是不用登录就能访问的
        http.formLogin()  //采用表单认证
                .loginPage("/login_page")  //请求登录页面
                .loginProcessingUrl("/login")  //登录页面提交url
                .defaultSuccessUrl("/login_success") //默认登录成功页面
                .and().csrf().disable();
        http.authorizeRequests().anyRequest().authenticated();   //所有端点的访问都必须进行登录，不能匿名访问
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return new WebSecurityCustomizer() {
            @Override
            public void customize(WebSecurity web) {
                web.ignoring().antMatchers("/login_page", "/css/*", "/js/*", "/image/*", "/fonts/*", "/test/*", "/test.html");
            }
        };
    }
}


package com.zerone.scms.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListenr implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        System.out.println("有人登录了");
        User user = (User) event.getAuthentication().getPrincipal();
        System.out.println(user.getUsername());
        System.out.println(user.getAuthorities());
        System.out.println(user.getPassword());
        System.out.println(event.getTimestamp());
        System.out.println(event.getAuthentication());
        System.out.println(event.getSource());
    }
}

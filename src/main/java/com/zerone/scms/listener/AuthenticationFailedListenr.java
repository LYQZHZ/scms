package com.zerone.scms.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailedListenr implements ApplicationListener<AbstractAuthenticationFailureEvent> {


    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        System.out.println("登陆失败");
    }
}

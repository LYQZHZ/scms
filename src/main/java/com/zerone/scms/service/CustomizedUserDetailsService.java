package com.zerone.scms.service;

import com.zerone.scms.controller.UserController;
import com.zerone.scms.model.Authority;
import com.zerone.scms.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class CustomizedUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(CustomizedUserDetailsService.class);
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.zerone.scms.model.User loginUser = null;
        User user = null;
        String authoritiesString = "";
        try {
            loginUser = userService.queryUserByUserName(username);
            if (!loginUser.getEnabled()) {
                throw new UsernameNotFoundException("用户不可用！");
            }

        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            throw new UsernameNotFoundException("读取数据库出错！");
        }
        if (loginUser != null) {
            Set<String> authoritiesSet = new HashSet<>();
            Role role = loginUser.getRole();
            if (role != null) {
                String roleName = role.getRoleName();
                authoritiesSet.add("ROLE_" + roleName);
                List<Authority> authorities = role.getAuthorities();
                if (authorities != null) {
                    for (int j = 0; j < authorities.size(); j++) {
                        Authority authority = authorities.get(j);
                        String authorityName = authority.getAuthorityName();
                        authoritiesSet.add(authorityName);

                    }
                    Iterator<String> iterator = authoritiesSet.iterator();
                    while (iterator.hasNext()) {
                        String authority = iterator.next();
                        if (authoritiesString.length() > 0) {
                            authoritiesString = authoritiesString + "," + authority;
                        } else {
                            authoritiesString = authoritiesString + authority;
                        }
                    }
                }
            }
            List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesString);
            user = new User(loginUser.getUsername(), loginUser.getPassword(), authorityList);
        } else {
            throw new UsernameNotFoundException("用户不存在！");
        }
        return user;
    }
}

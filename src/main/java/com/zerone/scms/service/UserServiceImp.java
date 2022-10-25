package com.zerone.scms.service;

import com.zerone.scms.mapper.UserMapper;
import com.zerone.scms.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Service("userService")
@Component("userService")
public class UserServiceImp implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    @Autowired
    private UserMapper userMapper;

    /**
     * 插入一个用户，同时插入用户-角色关系
     *
     * @param user 要保存的User对象
     * @return 用户的Id
     */
    @Override
    @Transactional
    public Boolean saveUser(User user) {
        boolean result = true;
        if (userMapper.insertUser(user) != 1) {
            result = false;
            throw new TransientDataAccessResourceException("用户信息保存失败");
        }
        return result;
    }

    @Override
    @Transactional
    public boolean saveUsers(List<User> users) {
        boolean result = true;
        if (users.size() != userMapper.insertUsers(users)) {
            result = false;
            throw new TransientDataAccessResourceException("用户信息保存失败");
        }
        return result;
    }

    @Override
    public User queryUserByUserName(String username) {
        User user = userMapper.selectUserByUserName(username);
        return user;
    }

    @Override
    public List<User> queryUsersByUserName(String username) {
        return userMapper.selectUsersByUserName(username);
    }


    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAllUser();
    }

    @Override
    public Boolean modifyUserPassword(String username, String oldpassword, String newpassword) {
        boolean result = false;
        User user = userMapper.selectUserByUserName(username);
        if (user != null) {
            String password = user.getPassword();
            if (new BCryptPasswordEncoder().matches(oldpassword, password)) {
                user.setPassword(new BCryptPasswordEncoder().encode(newpassword));
                result = userMapper.updateUser(user) == 1 ? true : false;
            }
        }
        return result;
    }

    @Override
    public Boolean initPassword(Long userId) {
        boolean result = false;
        User user = userMapper.selectUserByUserId(userId);
        if (user != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
            result = userMapper.updateUser(user) == 1 ? true : false;
        }
        return result;
    }

    @Override
    public Boolean enabledOrDisabledUser(Long userId) {
        boolean result = false;
        User user = userMapper.selectUserByUserId(userId);
        if (user != null) {
            user.setEnabled(!user.getEnabled());
            result = userMapper.updateUser(user) == 1 ? true : false;
        }
        return result;
    }
}

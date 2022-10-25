package com.zerone.scms.service;

import com.zerone.scms.mapper.RoleMapper;
import com.zerone.scms.mapper.UserMapper;
import com.zerone.scms.model.Role;
import com.zerone.scms.model.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImpTest {
    Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleService roleService;

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            if (i < 10) {
                user.setUsername("a000000" + i);
            } else {
                user.setUsername("a00000" + i);
            }
            user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
            user.setEnabled(true);
            Role role = roleMapper.getRoleByRoleName("academic");
            user.setRole(role);
            users.add(user);
        }
        List<User> oldUsers = userService.getAllUsers();
        assertEquals(100, userService.saveUsers(users));
        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            for (int i = oldUsers.size(); i < allUsers.size(); i++) {
                if (user.getUserId().longValue() == allUsers.get(i).getUserId().longValue()) {
                    assertEquals(user, allUsers.get(i));
                }
            }
        }
    }

    @Test
    void saveUser() {

    }

    @Test
    void saveUsers() {
    }

    @Test
    void queryUserByUserName() {
        User user = new User();
        user.setUsername("atest001");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
        user.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("academic");
        user.setRole(role);
        assertEquals(1, userService.saveUser(user));
        User newUser = userService.queryUserByUserName(user.getUsername());
        assertEquals(user, newUser);
        assertTrue(new BCryptPasswordEncoder().matches(user.getUsername(), newUser.getPassword()));
    }

    @Test
    void queryUsersByUserName() {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            if (i < 10) {
                user.setUsername("atest00" + i);
            } else {
                user.setUsername("atest0" + i);
            }
            user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
            user.setEnabled(true);
            Role role = roleMapper.getRoleByRoleName("academic");
            user.setRole(role);
            users.add(user);
        }
        List<User> oldUsers = userService.queryUsersByUserName("atest00");
        assertEquals(100, userService.saveUsers(users));
        List<User> allUsers = userService.queryUsersByUserName("atest00");
        assertEquals(oldUsers.size() + 10, allUsers.size());
        for (User user : allUsers) {
            for (int i = oldUsers.size(); i < allUsers.size(); i++) {
                if (user.getUserId().longValue() == allUsers.get(i).getUserId().longValue()) {
                    assertEquals(user, allUsers.get(i));
                }
            }
        }
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            if (i < 10) {
                user.setUsername("a000000" + i);
            } else {
                user.setUsername("a00000" + i);
            }
            user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
            user.setEnabled(true);
            Role role = roleMapper.getRoleByRoleName("academic");
            user.setRole(role);
            users.add(user);
        }
        List<User> oldUsers = userService.getAllUsers();
        assertEquals(100, userService.saveUsers(users));
        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            for (int i = oldUsers.size(); i < allUsers.size(); i++) {
                if (user.getUserId().longValue() == allUsers.get(i).getUserId().longValue()) {
                    assertEquals(user, allUsers.get(i));
                }
            }
        }
    }

    @Test
    @Transactional
    @Rollback
    void modifyUserPassword() {
        User user = new User();
        user.setUsername("a0000001");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
        user.setEnabled(true);
        Role role = roleService.queryRoleByRoleName("academic");
        user.setRole(role);
        assertTrue(userService.saveUser(user));
        User user1 = userService.queryUserByUserName(user.getUsername());
        assertTrue(new BCryptPasswordEncoder().matches(user1.getUsername(), user1.getPassword()));

        assertTrue(userService.modifyUserPassword("a0000001", "a0000001", "12345678"));
        User user2 = userService.queryUserByUserName(user.getUsername());
        assertTrue(new BCryptPasswordEncoder().matches("12345678", user2.getPassword()));
    }

    @Test
    @Transactional
    @Rollback
    void initPassword() {
        User user = new User();
        user.setUsername("a0000001");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
        user.setEnabled(true);
        Role role = roleService.queryRoleByRoleName("academic");
        user.setRole(role);

        assertTrue(userService.saveUser(user));
        User user1 = userService.queryUserByUserName(user.getUsername());
        assertTrue(new BCryptPasswordEncoder().matches(user1.getUsername(), user1.getPassword()));

        assertTrue(userService.modifyUserPassword(user1.getUsername(), user1.getUsername(), "12345678"));
        User user2 = userService.queryUserByUserName(user.getUsername());
        assertTrue(new BCryptPasswordEncoder().matches("12345678", user2.getPassword()));
        assertFalse(new BCryptPasswordEncoder().matches(user2.getUsername(), user2.getPassword()));

        assertTrue(userService.initPassword(user.getUserId()));
        User user3 = userService.queryUserByUserName(user.getUsername());
        assertTrue(new BCryptPasswordEncoder().matches(user3.getUsername(), user3.getPassword()));
    }

    @Test
    @Transactional
    @Rollback
    void enabledOrDisabledUser() {
        User user = new User();
        user.setUsername("a0000001");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
        user.setEnabled(true);
        Role role = roleService.queryRoleByRoleName("academic");
        user.setRole(role);

        assertTrue(userService.saveUser(user));
        User newUser = userService.queryUserByUserName(user.getUsername());
        assertTrue(newUser.getEnabled());

        assertTrue(userService.enabledOrDisabledUser(newUser.getUserId()));
        User user1 = userService.queryUserByUserName(newUser.getUsername());
        assertFalse(user1.getEnabled());

        assertTrue(userService.enabledOrDisabledUser(newUser.getUserId()));
        User user2 = userService.queryUserByUserName(newUser.getUsername());
        assertTrue(user2.getEnabled());

    }
}
package com.zerone.scms.mapper;

import com.zerone.scms.model.Role;
import com.zerone.scms.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Test
    @Transactional
    @Rollback
    void insertUser() {
        User user = new User();
        user.setUsername("atest001");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
        user.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("academic");
        user.setRole(role);
        assertEquals(1, userMapper.insertUser(user));
        User newUser = userMapper.selectUserByUserId(user.getUserId());
        assertEquals(user, newUser);
    }

    @Test
    @Transactional
    @Rollback
    void insertUsers() {
        List<User> users = new ArrayList<>();
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
        List<User> oldUsers = userMapper.selectAllUser();
        assertEquals(100, userMapper.insertUsers(users));
        List<User> allUsers = userMapper.selectAllUser();

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
    void updateUser() {
        User user = new User();
        user.setUsername("atest001");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
        user.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("academic");
        user.setRole(role);
        assertEquals(1, userMapper.insertUser(user));
        User newUser = userMapper.selectUserByUserId(user.getUserId());
        newUser.setPassword(new BCryptPasswordEncoder().encode("12345678"));
        newUser.setEnabled(false);
        assertEquals(1, userMapper.updateUser(newUser));
        User lastUser = userMapper.selectUserByUserId(user.getUserId());
        assertEquals(newUser, lastUser);
        assertTrue(new BCryptPasswordEncoder().matches("12345678", lastUser.getPassword()));
    }

    @Test
    @Transactional
    @Rollback
    void deleteUser() {
        User user = new User();
        user.setUsername("atest001");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
        user.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("academic");
        user.setRole(role);
        assertEquals(1, userMapper.insertUser(user));
        User newUser = userMapper.selectUserByUserId(user.getUserId());
        assertNotNull(newUser);
        assertEquals(1, userMapper.deleteUserByUserId(user.getUserId()));
        User lastUser = userMapper.selectUserByUserId(user.getUserId());
        assertNull(lastUser);
    }

    @Test
    @Transactional
    @Rollback
    void selectAllUser() {
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
        List<User> oldUsers = userMapper.selectAllUser();
        assertEquals(100, userMapper.insertUsers(users));
        List<User> allUsers = userMapper.selectAllUser();
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
    void selectUserByUserId() {
        User user = new User();
        user.setUsername("atest001");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
        user.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("academic");
        user.setRole(role);
        assertEquals(1, userMapper.insertUser(user));
        User newUser = userMapper.selectUserByUserId(user.getUserId());
        assertEquals(user, newUser);
        assertTrue(new BCryptPasswordEncoder().matches(user.getUsername(), newUser.getPassword()));
    }

    @Test
    @Transactional
    @Rollback
    void selectUserByUserName() {
        User user = new User();
        user.setUsername("atest001");
        user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
        user.setEnabled(true);
        Role role = roleMapper.getRoleByRoleName("academic");
        user.setRole(role);
        assertEquals(1, userMapper.insertUser(user));
        User newUser = userMapper.selectUserByUserName(user.getUsername());
        assertEquals(user, newUser);
        assertTrue(new BCryptPasswordEncoder().matches(user.getUsername(), newUser.getPassword()));
    }

    @Test
    @Transactional
    @Rollback
    void selectUsersByUserName() {
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
        List<User> oldUsers = userMapper.selectUsersByUserName("atest00");
        assertEquals(100, userMapper.insertUsers(users));
        List<User> allUsers = userMapper.selectUsersByUserName("atest00");
        assertEquals(oldUsers.size() + 10, allUsers.size());
        for (User user : allUsers) {
            for (int i = oldUsers.size(); i < allUsers.size(); i++) {
                if (user.getUserId().longValue() == allUsers.get(i).getUserId().longValue()) {
                    assertEquals(user, allUsers.get(i));
                }
            }
        }
    }
}
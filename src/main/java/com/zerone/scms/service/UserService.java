package com.zerone.scms.service;

import com.zerone.scms.model.Role;
import com.zerone.scms.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    @Transactional
    boolean saveUsers(List<User> users);

    User queryUserByUserName(String username);

    List<User> queryUsersByUserName(String username);

    Boolean saveUser(User user);

    /**
     * @Description:修改用户密码
     * @return: 是否修改成功
     * @Author: 你的名字
     * @Date: username 用户名
     * @Date: newpassword 新密码
     */
    Boolean modifyUserPassword(String username, String oldpassword, String newpassword);

    /**
     * @Description:初始化用户密码 把用户密码重置为用户名
     * @return: 成功为真，失败为假
     * @Author: 你的名字
     * @Date:
     */
    Boolean initPassword(Long userId);

    /**
     * @Description:修改用户状态 把用户改为可用或者不可用
     * @return: 成功为真，失败为假
     * @Author: 你的名字
     * @Date:
     */
    Boolean enabledOrDisabledUser(Long userId);
}

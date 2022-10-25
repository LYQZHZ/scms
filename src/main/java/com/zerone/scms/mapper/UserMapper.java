package com.zerone.scms.mapper;

import com.zerone.scms.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT user_id,username,password,enabled,roles_id \n" +
            "FROM users \n" +
            "WHERE username = #{username}")
    @Results({
            @Result(id = true, column = "user_id", property = "userId"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "enabled", property = "enabled"),
            @Result(column = "roles_id", property = "role",
                    one = @One(select = "com.zerone.scms.mapper.RoleMapper.getRolesById", fetchType = FetchType.LAZY))
    })
    User selectUserByUserName(String username);

    /**
     * @Description:
     * @return:
     * @Author: 你的名字
     * @Date:
     */
    @Select("SELECT user_id,username,password,enabled \n" +
            "FROM users \n" +
            "WHERE username LIKE '%' #{username} '%' ")
    @Results({
            @Result(id = true, column = "user_id", property = "userId"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "enabled", property = "enabled"),
            @Result(column = "user_id", property = "role",
                    one = @One(select = "com.zerone.scms.mapper.RoleMapper.getRolesById", fetchType = FetchType.LAZY))
    })
    List<User> selectUsersByUserName(String username);

    @Select("SELECT user_id,username,password,enabled,roles_id \n" +
            "FROM users \n" +
            "WHERE user_id = #{userId}")
    @Results({
            @Result(id = true, column = "user_id", property = "userId"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "enabled", property = "enabled"),
            @Result(column = "roles_id", property = "role",
                    one = @One(select = "com.zerone.scms.mapper.RoleMapper.getRolesById", fetchType = FetchType.LAZY))
    })
    User selectUserByUserId(Long userId);

    @Select("SELECT user_id,username,password,enabled \n" +
            "FROM users")
    @Results({
            @Result(id = true, column = "user_id", property = "userId"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "enabled", property = "enabled"),
            @Result(column = "user_id", property = "role",
                    one = @One(select = "com.zerone.scms.mapper.RoleMapper.getRolesById", fetchType = FetchType.LAZY))
    })
    List<User> selectAllUser();

    @Insert("INSERT INTO `scdb_scms`.`users`\n" +
            "(`username`,\n" +
            "`password`,\n" +
            "`enabled`,\n" +
            "`roles_id`)\n" +
            "VALUES\n" +
            "(#{username},\n" +
            "#{password},\n" +
            "#{enabled},\n" +
            "#{role.id})")
    @Results({
            @Result(id = true, column = "user_id", property = "userId"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "enabled", property = "enabled"),
            @Result(column = "roles_id", property = "role.id")
    })
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(User user);

    @Insert("<script>" +
            "INSERT INTO `scdb_scms`.`users` (\n" +
            "`username`,\n" +
            "`password`,\n" +
            "`enabled`,\n" +
            "`roles_id`)\n" +
            "VALUES\n" +
            "<foreach collection=\"users\" item=\"user\" separator=\",\">" +
            "(#{user.username},\n" +
            "#{user.password},\n" +
            "#{user.enabled},\n" +
            "#{user.role.id})" +
            "</foreach>" +
            "</script>"
    )
    @Results({
            @Result(id = true, column = "user_id", property = "userId"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "enabled", property = "enabled"),
            @Result(column = "roles_id", property = "role.id"),
            @Result(column = "roles_id", property = "role",
                    one = @One(select = "com.zerone.scms.mapper.RoleMapper.getRolesById", fetchType = FetchType.LAZY))
    })
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUsers(List<User> users);
  /*  @Insert("INSERT INTO `scdb_scms`.`user_role`\n" +
            "(`users_id`,\n" +
            "`roles_id`)\n" +
            "VALUES\n" +
            "(#{userId},\n" +
            "#{roleId});")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "users_id", property = "userId"),
            @Result(column = "roles_id", property = "roleId")
    })
    int insertUserAndRole(UserRole userRole);*/

    /**
     * @Description:
     * @return: 影响的行数
     * @Author: 李岩青
     * @Date: user
     */
    @Update("UPDATE `scdb_scms`.`users`\n" +
            "SET\n" +
            "`username` = #{username},\n" +
            "`password` = #{password},\n" +
            "`enabled` = #{enabled}\n" +
            "WHERE `user_id` = #{userId};")
    @Results({
            @Result(id = true, column = "user_id", property = "userId"),
            @Result(column = "username", property = "username"),
            @Result(column = "password", property = "password"),
            @Result(column = "enabled", property = "enabled")
    })
    Long updateUser(User user);

    @Delete("DELETE FROM `scdb_scms`.`users`\n" +
            "WHERE user_id = #{userId};")
    Long deleteUserByUserId(Long userId);
}

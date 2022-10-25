package com.zerone.scms.mapper;

import com.zerone.scms.model.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("SELECT id,role_name,role_description \n" +
            "FROM roles \n" +
            "WHERE id  = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "role_name", property = "roleName"),
            @Result(column = "role_description", property = "roleDescription"),
            @Result(column = "id", property = "authorities",
                    many = @Many(select = "com.zerone.scms.mapper.AuthorityMapper.getAuthoritiesByRoleId", fetchType = FetchType.LAZY))
    })
    Role getRolesById(Long id);

    @Select("SELECT `roles`.`id`,\n" +
            "    `roles`.`role_name`,\n" +
            "    `roles`.`role_description`\n" +
            "FROM `scdb_scms`.`roles`\n" +
            "WHERE role_name = #{roleName}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "role_name", property = "roleName"),
            @Result(column = "role_description", property = "roleDescription"),
            @Result(column = "id", property = "authorities",
                    many = @Many(select = "com.zerone.scms.mapper.AuthorityMapper.getAuthoritiesByRoleId", fetchType = FetchType.LAZY))
    })
    Role getRoleByRoleName(String roleName);

}

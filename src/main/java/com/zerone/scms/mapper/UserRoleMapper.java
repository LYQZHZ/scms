package com.zerone.scms.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
    @Delete("DELETE FROM `scdb_scms`.`user_role`\n" +
            "WHERE users_id = #{userId};")
    Long deleteUserRoleByUserId(Long userId);
}

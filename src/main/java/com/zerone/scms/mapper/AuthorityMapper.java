package com.zerone.scms.mapper;

import com.zerone.scms.model.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AuthorityMapper {
    @Select("SELECT id,authority_name,authority_description \n" +
            "FROM authorities \n" +
            "WHERE id IN (SELECT authorities_id FROM role_authority Where roles_id = #{roleId})")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "authority_name", property = "authorityName"),
            @Result(column = "authority_description", property = "authorityDescription")
    })
    List<Authority> getAuthoritiesByRoleId(Long roleId);
}

package com.zerone.scms.service;

import com.zerone.scms.mapper.RoleMapper;
import com.zerone.scms.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImp implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role queryRoleByRoleName(String roleName) {
        return roleMapper.getRoleByRoleName(roleName);
    }

    @Override
    public Role queryRoleByRoleId(Long id) {
        return roleMapper.getRolesById(id);
    }
}

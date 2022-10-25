package com.zerone.scms.service;

import com.zerone.scms.model.Academic;
import com.zerone.scms.model.Role;

public interface RoleService {
    Role queryRoleByRoleName(String roleName);

    Role queryRoleByRoleId(Long id);
}

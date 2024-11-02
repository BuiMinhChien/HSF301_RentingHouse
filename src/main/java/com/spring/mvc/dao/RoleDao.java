package com.spring.mvc.dao;

import com.spring.mvc.entity.ERole;
import com.spring.mvc.entity.Role;

public interface RoleDao {
    Role findRoleByRoleName(ERole roleName);
    Role findRoleById(int roleId);
}

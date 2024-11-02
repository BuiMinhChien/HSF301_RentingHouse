package com.spring.mvc.service;

import com.spring.mvc.entity.ERole;
import com.spring.mvc.entity.Role;

public interface RoleService {
    Role findRoleByRoleName(ERole roleName);
    Role findRoleById(int id);
}

package com.spring.mvc.service.impl;

import com.spring.mvc.dao.RoleDao;
import com.spring.mvc.entity.ERole;
import com.spring.mvc.entity.Role;
import com.spring.mvc.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "roleService")
@Transactional(propagation = Propagation.REQUIRED)
public class RoleServiceImpl implements RoleService {
        private RoleDao roleDao;

    @Override
    public Role findRoleByRoleName(ERole roleName) {
        try{
        return roleDao.findRoleByRoleName(roleName);
    } catch (Exception ex) {
        ex.printStackTrace();
        return null;
    }
    }

    @Override
    public Role findRoleById(int id) {
        try{
        return roleDao.findRoleById(id);
    } catch (Exception ex) {
        ex.printStackTrace();
        return null;
    }
    }
}

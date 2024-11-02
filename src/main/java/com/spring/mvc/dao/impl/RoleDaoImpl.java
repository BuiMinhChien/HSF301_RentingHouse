package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.RoleDao;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.ERole;
import com.spring.mvc.entity.Role;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository(value = "roleDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class RoleDaoImpl implements RoleDao {

    private final SessionFactory sessionFactory;
    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public Role findRoleByRoleName(ERole roleName) {
        String hql = "FROM Role a WHERE a.name = : roleName";
        return sessionFactory.getCurrentSession().createQuery(hql, Role.class)
                .setParameter("roleName", roleName)
                .uniqueResult();
    }

    @Override
    public Role findRoleById(int roleId) {
        String hql = "FROM Role a WHERE a.id = : roleName";
        return sessionFactory.getCurrentSession().createQuery(hql, Role.class)
                .setParameter("roleId", roleId)
                .uniqueResult();
    }
}

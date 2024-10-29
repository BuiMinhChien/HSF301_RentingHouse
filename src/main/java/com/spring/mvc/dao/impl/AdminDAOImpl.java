package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.AdminDAO;
import com.spring.mvc.entity.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "adminDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class AdminDAOImpl implements AdminDAO {
    private final SessionFactory sessionFactory;
    public AdminDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Admin findByEmailAndPassword(String email, String password) {
        Session session = sessionFactory.getCurrentSession();
        Admin admin = null;
        Query<Admin> query = session.createQuery("FROM Admin WHERE email = :email AND password = :password", Admin.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        admin = query.uniqueResult();
        return admin;
    }
}

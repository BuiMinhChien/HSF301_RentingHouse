package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.AccountDAO;
import com.spring.mvc.entity.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "accountDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class AccountDAOImpl implements AccountDAO {
    private final SessionFactory sessionFactory;
    private Session session;

    public AccountDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        session = sessionFactory.getCurrentSession();
    }

    @Override
    public Account findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Account a WHERE a.username = :username";
        return session.createQuery(hql, Account.class)
                .setParameter("username", username)
                .uniqueResult();
    }

    @Override
    public Account findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Account a WHERE a.email = :email";
        return session.createQuery(hql, Account.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public boolean existsByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT count(a) FROM Account a WHERE a.username = :username";
        Long count = session.createQuery(hql, Long.class)
                .setParameter("username", username)
                .uniqueResult();
        return count != null && count > 0;
    }
}

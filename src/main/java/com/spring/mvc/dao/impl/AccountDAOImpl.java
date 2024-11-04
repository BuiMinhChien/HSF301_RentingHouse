package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.AccountDAO;
import com.spring.mvc.entity.Account;
import org.hibernate.NonUniqueResultException;
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



    public AccountDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
//        session = sessionFactory.getCurrentSession();
    }

    @Override
    public Account findByUsername(String username) {
        try {
            // If unique constraint on the username field is set, this should work without issues
            return (Account) sessionFactory.getCurrentSession().createQuery("FROM Account a WHERE a.username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (NonUniqueResultException e) {
            // Log the error or handle it appropriately
            System.err.println("Multiple accounts found with the same username: " + username);
            throw e;
        }
    }


    @Override
    public Account findByEmail(String email) {
        String hql = "FROM Account a WHERE a.email = : email";
        return sessionFactory.getCurrentSession().createQuery(hql, Account.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public boolean existsByUsername(String username) {
        String hql = "SELECT count(a) FROM Account a WHERE a.username = :username";
        Long count = sessionFactory.getCurrentSession().createQuery(hql, Long.class)
                .setParameter("username", username)
                .uniqueResult();
        return count != null && count > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        String hql = "SELECT count(a) FROM Account a WHERE a.email = :email";
        Long count = sessionFactory.getCurrentSession().createQuery(hql, Long.class)
                .setParameter("email", email)
                .uniqueResult();
        return count != null && count > 0;
    }

    @Override
    public void save(Account account) {
        sessionFactory.getCurrentSession().save(account);
    }

    @Override
    public void update(Account account) {
        sessionFactory.getCurrentSession().update(account);
    }

    @Override
    public Account findById(int id) {
        return sessionFactory.getCurrentSession().get(Account.class, id);
    }
}

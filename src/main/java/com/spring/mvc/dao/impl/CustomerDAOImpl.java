package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.CustomerDAO;
import com.spring.mvc.entity.Customer;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository(value = "CustomerDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class CustomerDAOImpl implements CustomerDAO {
    private SessionFactory sessionFactory;
    private Session session;

    public CustomerDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    @Override
    public Customer findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Customer> query = session.createQuery("from Customer where id = :id", Customer.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Customer> findAll() {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Customer> query = session.createQuery("from Customer", Customer.class);
        return query.getResultList();
    }

    @Override
    public void save(Customer customer) {
        session = sessionFactory.getCurrentSession();
        session.save(customer);
    }

    @Override
    public void update(Customer customer) {
        session = sessionFactory.getCurrentSession();
        session.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        session = sessionFactory.getCurrentSession();
        session.delete(customer);
    }
}

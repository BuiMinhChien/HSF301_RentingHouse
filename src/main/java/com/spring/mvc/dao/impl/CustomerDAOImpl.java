package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.CustomerDAO;
import com.spring.mvc.entity.Customer;
import com.spring.mvc.entity.House;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository(value = "customerDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class CustomerDAOImpl implements CustomerDAO {
    private SessionFactory sessionFactory;

    public CustomerDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Customer findById(int id) {
        return sessionFactory.getCurrentSession().get(Customer.class, id);
    }

    @Override
    public List<Customer> findAll() {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Customer> query = session.createQuery("from Customer", Customer.class);
        return query.getResultList();
    }

    @Override
    public void save(Customer customer) {
        sessionFactory.getCurrentSession().save(customer);
    }

    @Override
    public void update(Customer customer) {
        Session session = sessionFactory.getCurrentSession();
        Customer existingCustomer = session.get(Customer.class, customer.getId());

        if (existingCustomer != null) {
            // Update fields
            existingCustomer.setFullName(customer.getFullName());
            existingCustomer.setGender(customer.getGender());
            existingCustomer.setDateOfBirth(customer.getDateOfBirth());
            existingCustomer.setAddress(customer.getAddress());
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());
            existingCustomer.setIdIssuanceDate(customer.getIdIssuanceDate());
            existingCustomer.setIdIssuancePlace(customer.getIdIssuancePlace());
            existingCustomer.setIdCardFrontImage(existingCustomer.getIdCardFrontImage());
            existingCustomer.setIdCardBackImage(existingCustomer.getIdCardBackImage());

            // Save the updated customer
            session.update(existingCustomer);
        }
    }

    @Override
    public void delete(Customer customer) {
        sessionFactory.getCurrentSession().delete(customer);
    }
}

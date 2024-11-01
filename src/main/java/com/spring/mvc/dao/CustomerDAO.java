package com.spring.mvc.dao;

import com.spring.mvc.entity.Customer;

import java.util.List;

public interface CustomerDAO {
    public Customer findById(int id);
    public List<Customer> findAll();
    public void save(Customer customer);
    public void update(Customer customer);
    public void delete(Customer customer);
}

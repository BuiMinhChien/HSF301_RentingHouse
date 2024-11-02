package com.spring.mvc.service;

import com.spring.mvc.dto.ProfileDTO;
import com.spring.mvc.entity.Customer;

public interface CustomerService {

    public Customer findCustomerById(int id);
    public boolean save(Customer customer,int id);
    public void update(Customer customer);
}

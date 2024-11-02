package com.spring.mvc.service.impl;

import com.spring.mvc.dao.CustomerDAO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Customer;
import com.spring.mvc.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service(value = "customerService")
@Transactional(propagation = Propagation.REQUIRED)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }


    @Override
    public Customer findCustomerById(int id) {
        try {
            return customerDAO.findById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean save(Customer customer, int id) {
        // Tìm kiếm người dùng theo ID
        Customer existingUser = customerDAO.findById(id);

        if (existingUser != null) {
            // Cập nhật các thuộc tính cần thiết
            existingUser.setAddress(customer.getAddress());
            existingUser.setGender(customer.getGender());
            // Cập nhật thêm các thuộc tính khác nếu cần
            existingUser.setAddress(customer.getAddress());
            existingUser.setPhoneNumber(customer.getPhoneNumber());
            existingUser.setFullName(customer.getFullName());
            existingUser.setDateOfBirth(customer.getDateOfBirth());
            existingUser.setIdIssuanceDate(customer.getIdIssuanceDate());
            existingUser.setIdIssuancePlace(customer.getIdIssuancePlace());

            // Lưu thay đổi
            customerDAO.save(existingUser);
            return true;
        } else {
            throw new EntityNotFoundException("User not found with id " + id);
        }
    }

}

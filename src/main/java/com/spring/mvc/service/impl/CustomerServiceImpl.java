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
            customerDAO.update(existingUser);
            return true;
        } else {
            throw new EntityNotFoundException("User not found with id " + id);
        }
    }

    @Override
    public void update(Customer customer) {
        try {
            Customer existingAccount = customerDAO.findById(customer.getId());
            if (existingAccount != null) {
                // Update fields if they are modified
                existingAccount.setGender(customer.getGender());
                existingAccount.setAddress(customer.getAddress());
                existingAccount.setPhoneNumber(customer.getPhoneNumber());
                existingAccount.setFullName(customer.getFullName());
                existingAccount.setDateOfBirth(customer.getDateOfBirth());
                existingAccount.setIdIssuanceDate(customer.getIdIssuanceDate());
                existingAccount.setIdIssuancePlace(customer.getIdIssuancePlace());
                existingAccount.setIdCardFrontImage(existingAccount.getIdCardFrontImage());
                existingAccount.setIdCardBackImage(existingAccount.getIdCardBackImage());
                customerDAO.update(existingAccount);

                // Save the updated account

            } else {
                throw new RuntimeException("Account not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

}

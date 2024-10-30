package com.spring.mvc.service.impl;

import com.spring.mvc.dao.AdminDAO;
import com.spring.mvc.dto.AdminDTO;
import com.spring.mvc.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "adminService")
@Transactional(propagation = Propagation.REQUIRED)
public class AdminServiceImpl implements AdminService {
    private final AdminDAO adminDAO;
    public AdminServiceImpl(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }
    public Admin searchAdminByGmailAndPassword(String gmail, String password) {
        try {
            Admin admin = adminDAO.findByEmailAndPassword(gmail, password);
            return admin;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public boolean verifyAdmin(AdminDTO adminDTO) {
        try {
            Admin admin = adminDAO.findByEmailAndPassword(adminDTO.getEmail(), adminDTO.getPassword());
            return admin != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

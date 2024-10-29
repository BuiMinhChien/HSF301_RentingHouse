package com.spring.mvc.service;

import com.spring.mvc.dto.AdminDTO;
import com.spring.mvc.entity.Admin;

public interface AdminService {
    public Admin searchAdminByGmailAndPassword(String gmail, String password);
    public boolean verifyAdmin(AdminDTO adminDTO);
}

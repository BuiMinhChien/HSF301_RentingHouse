package com.spring.mvc.service;

import com.spring.mvc.dto.AdminDTO;

public interface AdminService {
    public Admin searchAdminByGmailAndPassword(String gmail, String password);
    public boolean verifyAdmin(AdminDTO adminDTO);
}

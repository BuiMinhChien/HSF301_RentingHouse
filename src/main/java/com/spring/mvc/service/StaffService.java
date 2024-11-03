package com.spring.mvc.service;

import com.spring.mvc.entity.Staff;

public interface StaffService {
    Staff findStaffById(int id);
    void saveStaff(Staff staff);
    void updateStaff(Staff staff);
}

package com.spring.mvc.dao;

import com.spring.mvc.entity.Staff;

public interface StaffDAO {
    Staff findById(int id);
    void save(Staff staff);
    void update(Staff staff);
    void delete(Staff staff);
}

package com.spring.mvc.service.impl;

import com.spring.mvc.dao.StaffDAO;
import com.spring.mvc.entity.Staff;
import com.spring.mvc.service.StaffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "staffService")
@Transactional(propagation = Propagation.REQUIRED)
public class StaffServiceImpl implements StaffService {

    private StaffDAO staffDAO;

    public StaffServiceImpl(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    @Override
    public Staff findStaffById(int id) {
        try {
            return staffDAO.findById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveStaff(Staff staff) {
    }

    @Override
    public void updateStaff(Staff staff) {

    }
}

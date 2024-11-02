package com.spring.mvc.service.impl;

import com.spring.mvc.dao.FireEquipmentDAO;
import com.spring.mvc.entity.FireEquipments;
import com.spring.mvc.service.FireEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireEquipmentServiceImpl implements FireEquipmentService {

    private FireEquipmentDAO fireEquipmentDAO;

    public FireEquipmentServiceImpl(FireEquipmentDAO fireEquipmentDAO) {
        this.fireEquipmentDAO = fireEquipmentDAO;
    }

    @Override
    public List<FireEquipments> findAll() {
        return fireEquipmentDAO.findAll();
    }
}

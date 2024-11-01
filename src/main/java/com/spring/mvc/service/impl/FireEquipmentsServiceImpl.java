package com.spring.mvc.service.impl;

import com.spring.mvc.dao.FireEquipmentsDAO;
import com.spring.mvc.dao.ImageDAO;
import com.spring.mvc.entity.FireEquipments;
import com.spring.mvc.entity.Tag;
import com.spring.mvc.service.FireEquipmentsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "fireEquipments")
@Transactional(propagation = Propagation.REQUIRED)
public class FireEquipmentsServiceImpl implements FireEquipmentsService {
    private final FireEquipmentsDAO fireEquipmentsDAO;
    public FireEquipmentsServiceImpl(FireEquipmentsDAO fireEquipmentsDAO) {
        this.fireEquipmentsDAO = fireEquipmentsDAO;
    }

    @Override
    public List<FireEquipments> getAllFireEquipments() {
        try {
            List<FireEquipments> list = fireEquipmentsDAO.getAllFireEquipments();
            return list;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public FireEquipments getFireEquipmentsById(int id) {
        try {
            FireEquipments fireEquipments = fireEquipmentsDAO.getFireEquipmentsById(id);
            return fireEquipments;
        } catch (Exception ex) {
            throw ex;
        }
    }
}

package com.spring.mvc.dao;

import com.spring.mvc.entity.FireEquipments;
import com.spring.mvc.entity.Tag;

import java.util.List;

public interface FireEquipmentsDAO {
    public List<FireEquipments> getAllFireEquipments();
    public FireEquipments getFireEquipmentsById(int id);
}

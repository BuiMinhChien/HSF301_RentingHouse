package com.spring.mvc.service;

import com.spring.mvc.entity.FireEquipments;

import java.util.List;

public interface FireEquipmentsService{
    public List<FireEquipments> getAllFireEquipments();
    public FireEquipments getFireEquipmentsById(int id);
}

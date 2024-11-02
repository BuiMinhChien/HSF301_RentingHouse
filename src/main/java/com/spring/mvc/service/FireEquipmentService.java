package com.spring.mvc.service;

import com.spring.mvc.entity.FireEquipments;

import java.util.List;

public interface FireEquipmentService {
    List<FireEquipments> findAll();
    FireEquipments findById(int id);
}

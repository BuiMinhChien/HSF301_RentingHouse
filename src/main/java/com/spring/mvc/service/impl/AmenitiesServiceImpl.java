package com.spring.mvc.service.impl;

import com.spring.mvc.dao.AmenitiesDAO;
import com.spring.mvc.dao.ImageDAO;
import com.spring.mvc.entity.Amenities;
import com.spring.mvc.entity.Tag;
import com.spring.mvc.service.AmenitiesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "amenitiesService")
@Transactional(propagation = Propagation.REQUIRED)
public class AmenitiesServiceImpl implements AmenitiesService {
    private final AmenitiesDAO amenitiesDAO;
    public AmenitiesServiceImpl(AmenitiesDAO amenitiesDAO) {
        this.amenitiesDAO = amenitiesDAO;
    }

    @Override
    public void save(Amenities amenities) {

    }

    @Override
    public void update(Amenities amenities) {

    }

    @Override
    public void delete(Amenities amenities) {

    }

    @Override
    public Amenities findById(int id) {
        return null;
    }

    @Override
    public List<Amenities> findAll() {
        try {
            List<Amenities> list = amenitiesDAO.findAll();
            return list;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<Amenities> findByName(String amenityName) {
        return List.of();
    }
}

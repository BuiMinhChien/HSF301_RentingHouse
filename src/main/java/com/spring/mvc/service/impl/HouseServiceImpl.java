package com.spring.mvc.service.impl;

import com.spring.mvc.dao.HouseDAO;
import com.spring.mvc.entity.House;
import com.spring.mvc.entity.News;
import com.spring.mvc.service.HouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "houseService")
@Transactional(propagation = Propagation.REQUIRED)
public class HouseServiceImpl implements HouseService {
    private HouseDAO houseDAO;
    public HouseServiceImpl(HouseDAO houseDAO) {
        this.houseDAO = houseDAO;
    }

    @Override
    public void save(House house) {
        houseDAO.save(house);
    }

    @Override
    public void delete(House house) {
        try {
            houseDAO.delete(house);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(House house) {

    }

    @Override
    public House findById(int id) {
        try {
            House house = houseDAO.findById(id);
            return house;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public House findByName(String name) {
        return null;
    }

    @Override
    public House findTop3ByOrderByUpdatedDateDesc() {
        return null;
    }

    @Override
    public List<House> filterHouses(String status, String province, String district, String ward) {
        return houseDAO.filterHouses(status, province, district, ward);
    }

    @Override
    public List<House> getAllHouses() {
        return houseDAO.findAll();
    }
}

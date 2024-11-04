package com.spring.mvc.service.impl;

import com.spring.mvc.dao.HouseOwnerDAO;
import com.spring.mvc.entity.HouseOwner;
import com.spring.mvc.service.HouseOwnerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseOwnerServiceImpl implements HouseOwnerService {
    private HouseOwnerDAO houseOwnerDAO;

    public HouseOwnerServiceImpl(HouseOwnerDAO houseOwnerDAO) {
        this.houseOwnerDAO = houseOwnerDAO;
    }

    @Override
    public void save(HouseOwner houseOwner) {
        houseOwnerDAO.save(houseOwner);
    }

    @Override
    public HouseOwner findByEmail(String email) {
        return houseOwnerDAO.findByEmail(email);
    }

    @Override
    public List<HouseOwner> findAll() {
        return houseOwnerDAO.findAll();
    }

    @Override
    public HouseOwner findById(int id) {
        return houseOwnerDAO.findById(id);
    }


}

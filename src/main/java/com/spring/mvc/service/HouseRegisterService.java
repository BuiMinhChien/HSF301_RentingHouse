package com.spring.mvc.service;

import com.spring.mvc.entity.HouseRegister;

import java.util.List;

public interface HouseRegisterService {
    public void save(HouseRegister register);
    public void update(HouseRegister register);
    public HouseRegister getByRegisterId(int id);
    public HouseRegister getByHouseIdAccountId(int houseId, int accountId);
    public List<HouseRegister> getAllByAccountId(int accountId);
}

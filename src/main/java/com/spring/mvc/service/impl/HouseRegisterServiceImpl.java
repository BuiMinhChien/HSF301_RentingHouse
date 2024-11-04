package com.spring.mvc.service.impl;

import com.spring.mvc.dao.HouseRegisterDAO;
import com.spring.mvc.dao.TagForNewsDAO;
import com.spring.mvc.entity.HouseRegister;
import com.spring.mvc.entity.Image;
import com.spring.mvc.service.HouseRegisterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "houseRegisterService")
@Transactional(propagation = Propagation.REQUIRED)
public class HouseRegisterServiceImpl implements HouseRegisterService {
    private final HouseRegisterDAO houseRegisterDAO;
    public HouseRegisterServiceImpl(HouseRegisterDAO houseRegisterDAO) {
        this.houseRegisterDAO = houseRegisterDAO;
    }
    @Override
    public void save(HouseRegister register) {
        try {
            houseRegisterDAO.save(register);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(HouseRegister register) {
        try {
            houseRegisterDAO.update(register);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public HouseRegister getByRegisterId(int id) {
        try {
            HouseRegister register = houseRegisterDAO.getByRegisterId(id);
            return register;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public HouseRegister getByHouseIdAccountId(int houseId, int accountId) {
        try {
            HouseRegister register = houseRegisterDAO.getByHouseIdAccountId(houseId, accountId);
            return register;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<HouseRegister> getAllByAccountId(int accountId) {
        try {
            List<HouseRegister> list = houseRegisterDAO.getAllByAccountId(accountId);
            return list;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<HouseRegister> getAllByHouseId(int houseId) {
        return houseRegisterDAO.getAllByHouseId(houseId);
    }

}

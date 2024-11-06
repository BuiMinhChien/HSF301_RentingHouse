package com.spring.mvc.service.impl;

import com.spring.mvc.dao.ContractDAO;
import com.spring.mvc.entity.Contract;
import com.spring.mvc.service.ContractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "contractService")
@Transactional(propagation = Propagation.REQUIRED)
public class ContractServiceImpl implements ContractService {
    private final ContractDAO contractDAO;

    public ContractServiceImpl(ContractDAO contractDAO) {
        this.contractDAO = contractDAO;
    }

    @Override
    public boolean save(Contract contract) {
        try {
            contractDAO.save(contract);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Contract> getAllContract() {
        try {
            List<Contract> contracts = contractDAO.getAllContract();
            return contracts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Contract getContractById(int id) {
        try {
            Contract contract = contractDAO.getContractById(id);
            return contract;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Contract getContractByHouseId(int houseId) {
        try {
            Contract contract = contractDAO.getContractByHouseId(houseId);
            return contract;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteContractById(int id) {
        try {
            contractDAO.deleteContractById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateContract(Contract contract) {
        try {
            contractDAO.updateContract(contract);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

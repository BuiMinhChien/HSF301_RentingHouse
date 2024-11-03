package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.ContractDAO;
import com.spring.mvc.entity.Contract;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "contractDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class ContractDAOImpl implements ContractDAO {
    private final SessionFactory sessionFactory;

    public ContractDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Contract contract) {
        sessionFactory.getCurrentSession().save(contract);
    }

    @Override
    public List<Contract> getAllContract() {
        List<Contract> contracts = null;
        TypedQuery<Contract> query = sessionFactory.getCurrentSession().createQuery("FROM Contract", Contract.class);
        contracts = query.getResultList();
        return contracts;
    }

    @Override
    public Contract getContractById(int id) {
        return sessionFactory.getCurrentSession().find(Contract.class, id);
    }

    @Override
    public Contract getContractByHouseId(int houseId) {
        Contract contract = null;
        TypedQuery<Contract> query = sessionFactory.getCurrentSession().createQuery("FROM Contract c WHERE c.house.id = :houseId", Contract.class);
        query.setParameter("houseId", houseId);
        contract = query.getSingleResult();
        return contract;
    }

    @Override
    public void deleteContractById(int id) {
        Contract contract = getContractById(id);
        sessionFactory.getCurrentSession().remove(contract);
    }

    @Override
    public void updateContract(Contract contract) {
        Contract existingContract = getContractById(contract.getId());
        existingContract.setHouse(contract.getHouse());
        existingContract.setOwner(contract.getOwner());
        existingContract.setAccount(contract.getAccount());
        existingContract.setCreated_by(contract.getCreated_by());
        existingContract.setDocument(contract.getDocument());
        existingContract.setPrice(contract.getPrice());
        existingContract.setDeposit(contract.getDeposit());
        existingContract.setLease_duration_day(contract.getLease_duration_day());
        existingContract.setMove_in_date(contract.getMove_in_date());
        existingContract.setSigned_date(contract.getSigned_date());
        existingContract.setCreated_date(contract.getCreated_date());
        sessionFactory.getCurrentSession().save(existingContract);
    }
}

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
    public void deleteContractById(int id) {
        Contract contract = getContractById(id);
        sessionFactory.getCurrentSession().remove(contract);
    }
}

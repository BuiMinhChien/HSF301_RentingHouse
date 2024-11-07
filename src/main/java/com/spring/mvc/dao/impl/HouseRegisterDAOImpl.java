package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.HouseRegisterDAO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.House;
import com.spring.mvc.entity.HouseRegister;
import com.spring.mvc.entity.Topic;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "houseRegisterDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class HouseRegisterDAOImpl implements HouseRegisterDAO {
    private final SessionFactory sessionFactory;
    public HouseRegisterDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(HouseRegister register) {
        sessionFactory.getCurrentSession().save(register);
    }

    @Override
    public void update(HouseRegister register) {
        HouseRegister oldRegister = (HouseRegister) sessionFactory.getCurrentSession().get(HouseRegister.class, register.getId());
        oldRegister.setId(register.getId());
        oldRegister.setResult(register.getResult());
        oldRegister.setAccount(register.getAccount());
        oldRegister.setHouse(register.getHouse());
        oldRegister.setDeposit_status(register.getDeposit_status());
        oldRegister.setRegistration_time(register.getRegistration_time());
        sessionFactory.getCurrentSession().save(oldRegister);
    }

    @Override
    public HouseRegister getByRegisterId(int id) {
        HouseRegister houseRegister = (HouseRegister) sessionFactory.getCurrentSession().get(HouseRegister.class, id);
        return houseRegister;
    }

    @Override
    public HouseRegister getByHouseIdAccountId(int houseId, int accountId) {
        String hql = "FROM HouseRegister hr WHERE hr.house.id = :houseId AND hr.account.id = :accountId";
        return sessionFactory.getCurrentSession().createQuery(hql, HouseRegister.class)
                .setParameter("houseId", houseId)
                .setParameter("accountId", accountId)
                .uniqueResult();
    }

    @Override
    public List<HouseRegister> getAllByAccountId(int accountId) {
        String hql = "FROM HouseRegister hr WHERE hr.account.id = :accountId";
        List<HouseRegister> list = sessionFactory.getCurrentSession().createQuery(hql, HouseRegister.class).setParameter("accountId", accountId).getResultList();
        return list;
    }

    @Override
    public List<HouseRegister> getAllByHouseId(int houseId) {
        String hql = "FROM HouseRegister hr WHERE hr.house.id = :houseId";
        return sessionFactory.getCurrentSession().createQuery(hql, HouseRegister.class)
                .setParameter("houseId", houseId)
                .getResultList();
    }

}

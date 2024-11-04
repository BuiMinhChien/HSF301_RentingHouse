package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.HouseOwnerDAO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.House;
import com.spring.mvc.entity.HouseOwner;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository(value = "HouseOwnerDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class HouseOwnerDAOImpl implements HouseOwnerDAO {
    private SessionFactory sessionFactory;

    public HouseOwnerDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(HouseOwner houseOwner) {
        sessionFactory.getCurrentSession().save(houseOwner);
    }

    @Override
    public void update(HouseOwner houseOwner) {
        Session session = sessionFactory.getCurrentSession();
        HouseOwner existing = session.get(HouseOwner.class, houseOwner.getId());
        if (existing != null) {
            existing.setAddress(houseOwner.getAddress());
            existing.setName(houseOwner.getName());
            existing.setPhone(houseOwner.getPhone());
            existing.setGender(houseOwner.getGender());
            existing.setEmail(houseOwner.getEmail());
            existing.setDob(houseOwner.getDob());
            session.update(existing);
        }
    }

    @Override
    public void delete(HouseOwner houseOwner) {
        sessionFactory.getCurrentSession().delete(houseOwner);
    }

    @Override
    public HouseOwner findById(int id) {
        return sessionFactory.getCurrentSession().get(HouseOwner.class, id);
    }

    @Override
    public List<HouseOwner> findAll() {
        TypedQuery<HouseOwner> query = sessionFactory.getCurrentSession().createQuery("from HouseOwner", HouseOwner.class);
        return query.getResultList();

    }

    @Override
    public List<HouseOwner> findByName(String houseName) {
        Session session = sessionFactory.openSession();
        TypedQuery<HouseOwner> query = session.createQuery(
                "from HouseOwner where name like :partialName", HouseOwner.class
        );
        query.setParameter("partialName", "%" + houseName + "%");

        List<HouseOwner> houseOwners = query.getResultList();
        session.close();
        return houseOwners;
    }

    @Override
    public HouseOwner findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM HouseOwner ho WHERE ho.email = : email";
        return sessionFactory.getCurrentSession().createQuery(hql, HouseOwner.class)
                .setParameter("email", email)
                .uniqueResult();
    }




}

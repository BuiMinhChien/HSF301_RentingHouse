package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.HouseOwnerDAO;
import com.spring.mvc.entity.House;
import com.spring.mvc.entity.HouseOwner;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository(value = "HouseOwnerDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class HouseOwnerImpl implements HouseOwnerDAO {
    private SessionFactory sessionFactory;
    private Session session;

    public HouseOwnerImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(HouseOwner houseOwner) {
        session.save(houseOwner);
    }

    @Override
    public void update(HouseOwner houseOwner) {
        session.save(houseOwner);
    }

    @Override
    public void delete(HouseOwner houseOwner) {
        session.delete(houseOwner);
    }

    @Override
    public HouseOwner findById(int id) {
        return session.get(HouseOwner.class, id);
    }

    @Override
    public List<HouseOwner> findAll() {
        TypedQuery<HouseOwner> query = session.createQuery("from HouseOwner", HouseOwner.class);
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

}

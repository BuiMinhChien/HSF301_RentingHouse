package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.FireEquipmentsDAO;
import com.spring.mvc.entity.FireEquipments;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "fireEquipmentsDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class FireEquipmentsDAOIpml implements FireEquipmentsDAO {
    private final SessionFactory sessionFactory;
    public FireEquipmentsDAOIpml(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<FireEquipments> getAllFireEquipments() {
        Session session = sessionFactory.getCurrentSession();
        List<FireEquipments> list = null;
        TypedQuery<FireEquipments> query = session.createQuery("FROM FireEquipments ", FireEquipments.class);
        list = query.getResultList();
        return list;
    }

    @Override
    public FireEquipments getFireEquipmentsById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(FireEquipments.class, id);
    }
}

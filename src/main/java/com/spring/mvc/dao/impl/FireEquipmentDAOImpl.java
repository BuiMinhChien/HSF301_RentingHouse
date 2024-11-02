package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.FireEquipmentDAO;
import com.spring.mvc.entity.FireEquipments;
import com.spring.mvc.entity.House;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository(value = "fireEquipmentDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class FireEquipmentDAOImpl implements FireEquipmentDAO {
    private final SessionFactory sessionFactory;
    public FireEquipmentDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<FireEquipments> findAll() {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<FireEquipments> query = session.createQuery("from FireEquipments", FireEquipments.class);
        List<FireEquipments>  fireEquipmentsList = query.getResultList();
        return fireEquipmentsList;
    }
}

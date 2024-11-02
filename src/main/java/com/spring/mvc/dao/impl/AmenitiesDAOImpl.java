package com.spring.mvc.dao.impl;
import com.spring.mvc.dao.AmenitiesDAO;
import com.spring.mvc.dao.ImageDAO;
import com.spring.mvc.entity.Amenities;
import com.spring.mvc.entity.Image;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "amenitiesDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class AmenitiesDAOImpl implements AmenitiesDAO {
    private final SessionFactory sessionFactory;

    public AmenitiesDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Amenities amenities) {
        sessionFactory.getCurrentSession().save(amenities);
    }

    @Override
    public void update(Amenities amenities) {
        sessionFactory.getCurrentSession().save(amenities);
    }

    @Override
    public void delete(Amenities amenities) {
        sessionFactory.getCurrentSession().remove(amenities);
    }

    @Override
    public Amenities findById(int id) {
        return (Amenities) sessionFactory.getCurrentSession().get(Amenities.class, id);
    }

    @Override
    public List<Amenities> findAll() {
        return sessionFactory.getCurrentSession().createQuery("from Amenities", Amenities.class).list();
    }

    @Override
    public List<Amenities> findByName(String amenityName) {
        try {
            String hql = "from Amenities where name = :name";
           return sessionFactory.getCurrentSession().createQuery(hql).setParameter("name", amenityName).list();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

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

@Repository(value = "AmenitiesDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class AmenitiesDAOImpl implements AmenitiesDAO {
    private final SessionFactory sessionFactory;
    private Session session;

    public AmenitiesDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.session = sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Amenities amenities) {
        session.save(amenities);
    }

    @Override
    public void update(Amenities amenities) {
        session.save(amenities);
    }

    @Override
    public void delete(Amenities amenities) {
        session.remove(amenities);
    }

    @Override
    public Amenities findById(int id) {
        return (Amenities) session.get(Amenities.class, id);
    }

    @Override
    public List<Amenities> findAll() {
        return session.createQuery("from Amenities").list();
    }

    @Override
    public List<Amenities> findByName(String amenityName) {
        try {
            String hql = "from Amenities where name = :name";
           return session.createQuery(hql).setParameter("name", amenityName).list();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

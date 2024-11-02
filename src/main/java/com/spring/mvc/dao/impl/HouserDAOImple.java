package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.HouseDAO;
import com.spring.mvc.entity.House;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "HouseDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class HouserDAOImple implements HouseDAO {
    private final SessionFactory sessionFactory;
    private Session session;

    public HouserDAOImple(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(House house) {
        session = sessionFactory.getCurrentSession();
        session.save(house);
    }

    @Override
    public void delete(House house) {
        session = sessionFactory.getCurrentSession();
        session.remove(house);
    }

    @Override
    public void update(House house) {
        session = sessionFactory.getCurrentSession();
        session.save(house);
    }

    @Override
    public House findById(int id) {
        session = sessionFactory.getCurrentSession();
        return session.get(House.class, id);
    }

    @Override
    public List<House> findAll() {
        session = sessionFactory.getCurrentSession();
        TypedQuery<House> query = session.createQuery("from House", House.class);
        List<House> houses = query.getResultList();
        session.close();
        return houses;
    }

    @Override
    public House findByName(String name) {
        session = sessionFactory.getCurrentSession();
        TypedQuery<House> query = session.createQuery("from House where name = :name", House.class);
        query.setParameter("name", name);
        House house = query.getSingleResult();
        session.close();
        return house;
    }

    @Override
    public House findTop3ByOrderByUpdatedDateDesc() {
        Session session = sessionFactory.getCurrentSession();
        Query<House> query = session.createQuery("from House order by updated_date desc", House.class);
        query.setMaxResults(3);
        return query.getSingleResult();
    }


}

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

@Repository(value = "houseDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class HouseDAOImpl implements HouseDAO {
    private final SessionFactory sessionFactory;

    public HouseDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(House house) {
        sessionFactory.getCurrentSession().save(house);
    }

    @Override
    public void delete(House house) {
        sessionFactory.getCurrentSession().remove(house);
    }

    @Override
    public void update(House house) {
        House existingHouse = findById(house.getId());
        existingHouse.setName(house.getName());
        existingHouse.setWard(house.getWard());
        existingHouse.setDistrict(house.getDistrict());
        existingHouse.setProvince(house.getProvince());
        existingHouse.setLocation(house.getLocation());
        existingHouse.setLand_space(house.getLand_space());
        existingHouse.setLiving_space(house.getLiving_space());
        existingHouse.setNumber_bed_room(house.getNumber_bed_room());
        existingHouse.setNumber_bath(house.getNumber_bath());
        existingHouse.setDescription(house.getDescription());
        existingHouse.setCoordinates_on_map(house.getCoordinates_on_map());
        existingHouse.setAvailable_status(house.getAvailable_status());
        existingHouse.setUpdated_by(house.getUpdated_by());
        existingHouse.setUpdated_date(house.getUpdated_date());
        existingHouse.setOwner(house.getOwner());
        sessionFactory.getCurrentSession().save(existingHouse);
    }

    @Override
    public House findById(int id) {
        return sessionFactory.getCurrentSession().get(House.class, id);
    }

    @Override
    public List<House> findAll() {
        // Sử dụng JOIN FETCH để lấy tất cả các mối quan hệ EAGER của House
        TypedQuery<House> query = sessionFactory.getCurrentSession().createQuery(
                "SELECT h FROM House h ",
                House.class
        );
        List<House> houses = query.getResultList();
        return houses;
    }

    @Override
    public House findByName(String name) {
        Session session = sessionFactory.openSession();
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

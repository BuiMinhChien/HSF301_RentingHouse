package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.HouseDAO;
import com.spring.mvc.entity.House;
import com.spring.mvc.entity.HouseOwner;
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
        sessionFactory.getCurrentSession().save(house);
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

    @Override
    public List<House> findByHouseOwner(HouseOwner houseOwner) {
        // Lấy session hiện tại từ sessionFactory
        Session session = sessionFactory.getCurrentSession();

        // Tạo câu truy vấn HQL để tìm các House theo HouseOwner
        String hql = "FROM House h WHERE h.owner = :owner";

        // Tạo query từ HQL
        Query<House> query = session.createQuery(hql, House.class);
        query.setParameter("owner", houseOwner);

        // Thực thi truy vấn và trả về kết quả
        return query.getResultList();
    }


}

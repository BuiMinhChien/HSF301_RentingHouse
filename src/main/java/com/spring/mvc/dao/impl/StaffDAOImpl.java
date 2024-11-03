package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.StaffDAO;
import com.spring.mvc.entity.Staff;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository(value = "staffDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class StaffDAOImpl implements StaffDAO {
    private SessionFactory sessionFactory;

    public StaffDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Staff findById(int id) {
        return sessionFactory.getCurrentSession().get(Staff.class, id);
    }

    @Override
    public void save(Staff staff) {
        sessionFactory.getCurrentSession().save(staff);
    }

    @Override
    public void update(Staff staff) {
        sessionFactory.getCurrentSession().update(staff);
    }

    @Override
    public void delete(Staff staff) {
        sessionFactory.getCurrentSession().delete(staff);
    }
}

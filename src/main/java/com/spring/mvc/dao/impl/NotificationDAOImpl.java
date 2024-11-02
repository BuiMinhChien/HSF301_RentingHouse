package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.NotificationDAO;
import com.spring.mvc.entity.Notification;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "notificationDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class NotificationDAOImpl implements NotificationDAO {
    private final SessionFactory sessionFactory;

    public NotificationDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Notification notification) {
        sessionFactory.getCurrentSession().save(notification);
    }

    @Override
    public List<Notification> findByAccountIdOrderByCreatedDateDesc(int accountId) {
        String hql = "SELECT n FROM Notification n JOIN n.accounts a WHERE a.id = :accountId ORDER BY n.created_date DESC";
        return sessionFactory.getCurrentSession().createQuery(hql).setParameter("accountId", accountId).getResultList();
    }

    @Override
    public Notification findById(int id) {
        return sessionFactory.getCurrentSession().find(Notification.class, id);
    }
}

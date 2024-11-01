package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.ImageDAO;
import com.spring.mvc.entity.Image;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "imageDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class ImageDAOImpl implements ImageDAO {
    private final SessionFactory sessionFactory;
    public ImageDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Image findImageById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Image.class, id);
    }

    @Override
    public void saveImage(Image image) {
        Session session = sessionFactory.getCurrentSession();
        session.save(image);
    }

    @Override
    public Image getDefaultAvatar() {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Image.class, 1);
    }
}

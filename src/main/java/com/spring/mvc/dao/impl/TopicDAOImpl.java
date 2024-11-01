package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.NewsDAO;
import com.spring.mvc.dao.TopicDAO;
import com.spring.mvc.entity.Topic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "topicDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class TopicDAOImpl implements TopicDAO {

    private final SessionFactory sessionFactory;

    public TopicDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Topic findById(int topicId) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Topic.class, topicId);
    }

    @Override
    public void save(Topic topic) {
        Session session = sessionFactory.getCurrentSession();
        session.save(topic);
    }

    @Override
    public void deleteById(int topicId) {
        Session session = sessionFactory.getCurrentSession();
        Topic topic = findById(topicId);
        if (topic != null) {
            session.remove(topic);
        }
    }

    @Override
    public List<Topic> findByParentTopicIsNull() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Topic t WHERE t.parent_topic IS NULL";
        return session.createQuery(hql, Topic.class).getResultList();
    }

    @Override
    public List<Topic> findByParentTopic_TopicId(int parentId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Topic t WHERE t.parent_topic.id = :parentId";
        return session.createQuery(hql, Topic.class)
                .setParameter("parentId", parentId)
                .getResultList();
    }
}

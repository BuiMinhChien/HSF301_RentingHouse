package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.NewsDAO;
import com.spring.mvc.dao.QuestionDAO;
import com.spring.mvc.dao.TopicDAO;
import com.spring.mvc.entity.Topic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        return sessionFactory.getCurrentSession().find(Topic.class, topicId);
    }

    @Override
    public void save(Topic topic) {
        sessionFactory.getCurrentSession().save(topic);
    }

    @Override
    public void deleteById(int topicId) {
        Topic topic = findById(topicId);
        if (topic != null) {
            sessionFactory.getCurrentSession().remove(topic);
        }
    }

    @Override
    public List<Topic> findByParentTopicIsNull() {
        String hql = "FROM Topic t WHERE t.parent_topic IS NULL";
        List<Topic> list = sessionFactory.getCurrentSession().createQuery(hql, Topic.class).getResultList();
        return list;
    }

    @Override
    public List<Topic> findTopicsWithoutQuestions() {
        String hql = "FROM Topic t WHERE t.subTopics IS NOT EMPTY";
        return sessionFactory.getCurrentSession().createQuery(hql, Topic.class).getResultList();
    }

    @Override
    public List<Topic> findByParentTopic_TopicId(int parentId) {
        String hql = "FROM Topic t WHERE t.parent_topic.id = " + parentId;
        List<Topic> list = sessionFactory.getCurrentSession().createQuery(hql, Topic.class).getResultList();
        return list;
    }

    @Override
    public List<Topic> findBySubTopicsIsEmpty() {
        String hql = "FROM Topic t WHERE t.subTopics IS EMPTY";
        return sessionFactory.getCurrentSession().createQuery(hql, Topic.class).getResultList();
    }
}

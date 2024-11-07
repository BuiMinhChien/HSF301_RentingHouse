package com.spring.mvc.service.impl;

import com.spring.mvc.dao.TopicDAO;
import com.spring.mvc.entity.Topic;
import com.spring.mvc.service.TopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value = "topicService")
@Transactional(propagation = Propagation.REQUIRED)
public class TopicServiceImpl implements TopicService {
    private final TopicDAO topicDAO;

    public TopicServiceImpl(TopicDAO topicDAO) {
        this.topicDAO = topicDAO;
    }

    @Override
    public List<Topic> getMainTopics() {
        return topicDAO.findByParentTopicIsNull();
    }

    @Override
    public List<Topic> getSubTopics(int parentId) {
        return topicDAO.findByParentTopic_TopicId(parentId);
    }

    @Override
    public Topic getTopicById(int topicId) {
        return topicDAO.findById(topicId);
    }

    @Override
    public boolean updateTopicName(int topicId, String newTopicName) {
        Topic topic = topicDAO.findById(topicId);
        if (topic != null) {
            topic.setTopic_name(newTopicName);
            topicDAO.save(topic);
            return true;
        }
        return false;
    }

    @Override
    public void deleteTopic(int topicId) {
        topicDAO.deleteById(topicId);
    }

    @Override
    public boolean saveMainTopic(String topicName) {
        try {
            Topic topic = new Topic();
            topic.setTopic_name(topicName);
            topicDAO.save(topic);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveSubTopic(int parentId, String subTopicName) {
        try {
            Topic topic = new Topic();
            Topic parentTopic = topicDAO.findById(parentId);
            topic.setParent_topic(parentTopic);
            topic.setTopic_name(subTopicName);
            topicDAO.save(topic);
            parentTopic.addSubTopic(topic);
            topicDAO.save(parentTopic);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Topic> getALlSubTopics() {
        return topicDAO.findBySubTopicsIsEmpty();
    }

    @Override
    public List<Topic> findTopicsWithoutQuestions() {
        return topicDAO.findTopicsWithoutQuestions();
    }
}

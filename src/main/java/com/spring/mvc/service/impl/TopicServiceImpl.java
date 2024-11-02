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
}

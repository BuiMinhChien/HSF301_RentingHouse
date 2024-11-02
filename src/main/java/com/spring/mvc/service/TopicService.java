package com.spring.mvc.service;

import com.spring.mvc.entity.Topic;

import java.util.List;
import java.util.Optional;

public interface TopicService {
    public List<Topic> getMainTopics();
    public List<Topic> getSubTopics(int parentId);
    public Topic getTopicById(int topicId);
    public boolean updateTopicName(int topicId, String newTopicName);
    public void deleteTopic(int topicId);
}

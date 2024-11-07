package com.spring.mvc.dao;

import com.spring.mvc.entity.Topic;

import java.util.List;

public interface TopicDAO {
    Topic findById(int topicId);

    void save(Topic topic);

    void deleteById(int topicId);

    List<Topic> findByParentTopicIsNull();

    public List<Topic> findTopicsWithoutQuestions();

    List<Topic> findByParentTopic_TopicId(int parentId);

    List<Topic> findBySubTopicsIsEmpty();
}

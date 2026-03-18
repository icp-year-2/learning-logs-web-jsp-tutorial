package com.learninglogs.dao;

import com.learninglogs.entity.Topic;
import java.util.ArrayList;

/**
 * Topic DAO Interface — defines database operations for topics.
 * Complete from Week 2: insertTopic, fetchAllTopics, findTopicByName.
 */
public interface TopicDao {
    boolean insertTopic(Topic topic);
    ArrayList<Topic> fetchAllTopics();
    Topic findTopicByName(String name);

    Topic findTopicById(int id);
    boolean updateTopic(Topic topic);
    boolean deleteTopic(int id);
    ArrayList<Topic> searchTopics(String keyword);
}

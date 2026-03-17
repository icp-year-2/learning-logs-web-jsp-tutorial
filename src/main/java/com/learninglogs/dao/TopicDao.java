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

    // ============================================================
    // TODO 13: Add New DAO Method Signatures
    // ============================================================
    // Add THREE new method signatures for full CRUD support:
    //
    //   1. Find a topic by its ID (needed for edit form pre-population):
    //      Topic findTopicById(int id)
    //
    //   2. Update an existing topic's name:
    //      boolean updateTopic(Topic topic)
    //
    //   3. Delete a topic by its ID:
    //      boolean deleteTopic(int id)
    //
    // WHY: The servlet needs these operations to handle edit and
    //      delete requests. The interface defines WHAT the DAO can
    //      do — the implementation (TopicDaoImpl) defines HOW.
    //
    // The complete code:
    //
    //   Topic findTopicById(int id);
    //   boolean updateTopic(Topic topic);
    //   boolean deleteTopic(int id);
    //
    // ============================================================
}

package com.learninglogs.dao;

import com.learninglogs.entity.Topic;
import com.learninglogs.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Topic DAO Implementation — JDBC operations for topics.
 * Complete from Week 2: insertTopic, fetchAllTopics, findTopicByName.
 * Week 4 adds: findTopicById, updateTopic, deleteTopic.
 */
public class TopicDaoImpl implements TopicDao {

    @Override
    public boolean insertTopic(Topic topic) {
        if (findTopicByName(topic.getName()) != null) {
            System.out.println("Topic already exists: " + topic.getName());
            return false;
        }

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO topics (name) VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, topic.getName());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error inserting topic: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    @Override
    public ArrayList<Topic> fetchAllTopics() {
        ArrayList<Topic> topics = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM topics";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Topic topic = new Topic(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
                topics.add(topic);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching topics: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return topics;
    }

    @Override
    public Topic findTopicByName(String name) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM topics WHERE LOWER(name) = LOWER(?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Topic(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error finding topic: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return null;
    }

    // ============================================================
    // TODO 13: Implement New DAO Methods
    // ============================================================
    // Implement the THREE new methods from TopicDao interface.
    // Follow the same JDBC pattern as the existing methods above:
    //   1. Get connection
    //   2. Prepare SQL with ? placeholders
    //   3. Set parameters
    //   4. Execute query/update
    //   5. Close connection in finally block
    //
    // 1. findTopicById(int id):
    //    - SQL: SELECT * FROM topics WHERE id = ?
    //    - Returns a Topic object, or null if not found
    //
    // 2. updateTopic(Topic topic):
    //    - SQL: UPDATE topics SET name = ? WHERE id = ?
    //    - Returns true on success, false on error
    //
    // 3. deleteTopic(int id):
    //    - SQL: DELETE FROM topics WHERE id = ?
    //    - Returns true on success, false on error
    //    - Note: entries are deleted automatically (ON DELETE CASCADE)
    //
    // The complete code:
    //
    //   @Override
    //   public Topic findTopicById(int id) {
    //       Connection conn = null;
    //       try {
    //           conn = DatabaseConnection.getConnection();
    //           String sql = "SELECT * FROM topics WHERE id = ?";
    //           PreparedStatement statement = conn.prepareStatement(sql);
    //           statement.setInt(1, id);
    //           ResultSet rs = statement.executeQuery();
    //           if (rs.next()) {
    //               return new Topic(
    //                   rs.getInt("id"),
    //                   rs.getString("name"),
    //                   rs.getTimestamp("created_at"),
    //                   rs.getTimestamp("updated_at")
    //               );
    //           }
    //       } catch (SQLException e) {
    //           System.out.println("Error finding topic: " + e.getMessage());
    //       } finally {
    //           DatabaseConnection.closeConnection(conn);
    //       }
    //       return null;
    //   }
    //
    //   @Override
    //   public boolean updateTopic(Topic topic) {
    //       Connection conn = null;
    //       try {
    //           conn = DatabaseConnection.getConnection();
    //           String sql = "UPDATE topics SET name = ? WHERE id = ?";
    //           PreparedStatement statement = conn.prepareStatement(sql);
    //           statement.setString(1, topic.getName());
    //           statement.setInt(2, topic.getId());
    //           statement.executeUpdate();
    //           return true;
    //       } catch (SQLException e) {
    //           System.out.println("Error updating topic: " + e.getMessage());
    //           return false;
    //       } finally {
    //           DatabaseConnection.closeConnection(conn);
    //       }
    //   }
    //
    //   @Override
    //   public boolean deleteTopic(int id) {
    //       Connection conn = null;
    //       try {
    //           conn = DatabaseConnection.getConnection();
    //           String sql = "DELETE FROM topics WHERE id = ?";
    //           PreparedStatement statement = conn.prepareStatement(sql);
    //           statement.setInt(1, id);
    //           statement.executeUpdate();
    //           return true;
    //       } catch (SQLException e) {
    //           System.out.println("Error deleting topic: " + e.getMessage());
    //           return false;
    //       } finally {
    //           DatabaseConnection.closeConnection(conn);
    //       }
    //   }
    //
    // ============================================================

    @Override
    public Topic findTopicById(int id) {
        return null; // TODO 13: Implement this method
    }

    @Override
    public boolean updateTopic(Topic topic) {
        return false; // TODO 13: Implement this method
    }

    @Override
    public boolean deleteTopic(int id) {
        return false; // TODO 13: Implement this method
    }

    // ============================================================
    // TODO 14: Implement Search Topics
    // ============================================================
    // Implement the searchTopics method to find topics by keyword.
    //
    // Steps:
    //   1. Get connection
    //   2. SQL: SELECT * FROM topics WHERE LOWER(name) LIKE LOWER(?)
    //   3. Set parameter with wildcards: "%" + keyword + "%"
    //   4. Execute query and build ArrayList<Topic>
    //   5. Close connection in finally block
    //
    // CONCEPTS:
    // - SQL LIKE with % wildcards matches partial text:
    //   "Py" matches "Python", "PyGame", etc.
    // - LOWER() on both sides makes the search case-insensitive:
    //   searching "py" finds "Python"
    // - This follows the same pattern as fetchAllTopics() but with
    //   a WHERE clause to filter results.
    //
    // The complete code:
    //
    //   @Override
    //   public ArrayList<Topic> searchTopics(String keyword) {
    //       ArrayList<Topic> topics = new ArrayList<>();
    //       Connection conn = null;
    //       try {
    //           conn = DatabaseConnection.getConnection();
    //           String sql = "SELECT * FROM topics WHERE LOWER(name) LIKE LOWER(?)";
    //           PreparedStatement statement = conn.prepareStatement(sql);
    //           statement.setString(1, "%" + keyword + "%");
    //           ResultSet rs = statement.executeQuery();
    //           while (rs.next()) {
    //               Topic topic = new Topic(
    //                   rs.getInt("id"),
    //                   rs.getString("name"),
    //                   rs.getTimestamp("created_at"),
    //                   rs.getTimestamp("updated_at")
    //               );
    //               topics.add(topic);
    //           }
    //       } catch (SQLException e) {
    //           System.out.println("Error searching topics: " + e.getMessage());
    //       } finally {
    //           DatabaseConnection.closeConnection(conn);
    //       }
    //       return topics;
    //   }
    //
    // ============================================================

    @Override
    public ArrayList<Topic> searchTopics(String keyword) {
        return new ArrayList<>(); // TODO 14: Implement this method
    }
}

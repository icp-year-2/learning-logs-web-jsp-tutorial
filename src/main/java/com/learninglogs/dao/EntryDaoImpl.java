package com.learninglogs.dao;

import com.learninglogs.entity.Entry;
import com.learninglogs.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Entry DAO Implementation — JDBC operations for entries.
 * Updated for Week 4: includes title, link, and image fields.
 */
public class EntryDaoImpl implements EntryDao {

    @Override
    public boolean insertEntry(Entry entry) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO entries (topic_id, title, text, link, image) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, entry.getTopicId());
            statement.setString(2, entry.getTitle());
            statement.setString(3, entry.getText());
            statement.setString(4, entry.getLink());
            statement.setString(5, entry.getImage());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error inserting entry: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
    }

    @Override
    public ArrayList<Entry> fetchAllEntries() {
        ArrayList<Entry> entries = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM entries";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Entry entry = new Entry(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("text"),
                    rs.getInt("topic_id"),
                    rs.getString("link"),
                    rs.getString("image"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
                entries.add(entry);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching entries: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return entries;
    }

    @Override
    public ArrayList<Entry> fetchEntriesByTopicId(int topicId) {
        ArrayList<Entry> entries = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM entries WHERE topic_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, topicId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Entry entry = new Entry(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("text"),
                    rs.getInt("topic_id"),
                    rs.getString("link"),
                    rs.getString("image"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
                entries.add(entry);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching entries by topic: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn);
        }
        return entries;
    }
}

package com.learninglogs.entity;

import java.sql.Timestamp;

/**
 * Topic entity — represents a learning topic.
 * Maps to the `topics` table in the database.
 *
 * Complete from Week 2. Added setId() for Week 4 edit functionality.
 */
public class Topic {

    private int id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Topic(String name) {
        this.name = name;
    }

    public Topic(int id, String name, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "[" + id + "] " + name + " (Created: " + createdAt + ")";
    }
}

package com.learninglogs.dao;

import com.learninglogs.entity.Entry;
import java.util.ArrayList;

/**
 * Entry DAO Interface — defines database operations for entries.
 * Complete from Week 2. No changes needed.
 */
public interface EntryDao {
    boolean insertEntry(Entry entry);
    ArrayList<Entry> fetchAllEntries();
    ArrayList<Entry> fetchEntriesByTopicId(int topicId);
}

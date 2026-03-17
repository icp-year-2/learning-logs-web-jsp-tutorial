-- ============================================================
-- Learning Logs Database — Schema
-- ============================================================
-- Run this file FIRST in phpMyAdmin to create the database
-- and tables. Then run seed.sql to add sample data.
-- ============================================================

-- Create and Use Database
CREATE DATABASE IF NOT EXISTS learning_logs;
USE learning_logs;

-- Drop existing tables for a clean install
DROP TABLE IF EXISTS entries;
DROP TABLE IF EXISTS topics;

-- Topics table
CREATE TABLE topics (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Entries table (updated for Week 4 — added title, link, image)
CREATE TABLE entries (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  topic_id INT NOT NULL,
  title VARCHAR(200) NOT NULL,
  text TEXT NOT NULL,
  link VARCHAR(500),
  image VARCHAR(500),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (topic_id) REFERENCES topics(id) ON DELETE CASCADE
);

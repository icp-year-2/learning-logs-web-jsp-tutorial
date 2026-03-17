-- ============================================================
-- Learning Logs Database — Sample Data
-- ============================================================
-- Run this file AFTER learninglog.sql to add sample data.
-- ============================================================

USE learning_logs;

-- Sample topics
INSERT INTO topics (name) VALUES
('Python'),
('Web Development'),
('Data Science'),
('Machine Learning'),
('Cybersecurity');

-- Sample entries (with new title, link, image fields)
INSERT INTO entries (topic_id, title, text, link, image) VALUES
(1, 'Variables and Data Types', 'Python uses dynamic typing. Variables do not need explicit type declarations.', 'https://docs.python.org/3/tutorial/introduction.html', NULL),
(1, 'Control Flow', 'if/elif/else statements and for/while loops are the main control flow tools.', NULL, NULL),
(2, 'HTML Basics', 'HTML provides the structure of a web page using elements and tags.', 'https://developer.mozilla.org/en-US/docs/Learn/HTML', NULL),
(2, 'CSS Flexbox', 'Flexbox is a one-dimensional layout method for arranging items in rows or columns.', 'https://css-tricks.com/snippets/css/a-guide-to-flexbox/', NULL),
(3, 'Introduction to Pandas', 'Pandas provides DataFrames for structured data analysis in Python.', 'https://pandas.pydata.org/docs/', NULL);

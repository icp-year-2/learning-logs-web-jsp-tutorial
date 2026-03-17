-- ============================================================
-- Learning Logs Database — Sample Data
-- ============================================================
-- Run this file AFTER learninglog.sql to add sample data.
-- ============================================================

USE learning_logs;

-- Clear existing data before seeding
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE entries;
TRUNCATE TABLE topics;
SET FOREIGN_KEY_CHECKS = 1;

-- Sample topics
INSERT INTO topics (name) VALUES
('Python'),
('Web Development'),
('Data Science'),
('Machine Learning'),
('Cybersecurity');

-- Sample entries (with new title, link, image fields)
INSERT INTO entries (topic_id, title, text, link, image) VALUES
(1, 'Variables and Data Types', 'Python uses dynamic typing. Variables do not need explicit type declarations.', 'https://docs.python.org/3/tutorial/introduction.html', 'https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Python-logo-notext.svg/800px-Python-logo-notext.svg.png'),
(1, 'Control Flow', 'if/elif/else statements and for/while loops are the main control flow tools.', NULL, NULL),
(2, 'HTML Basics', 'HTML provides the structure of a web page using elements and tags.', 'https://developer.mozilla.org/en-US/docs/Learn/HTML', 'https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/HTML5_logo_and_wordmark.svg/800px-HTML5_logo_and_wordmark.svg.png'),
(2, 'CSS Flexbox', 'Flexbox is a one-dimensional layout method for arranging items in rows or columns.', 'https://css-tricks.com/snippets/css/a-guide-to-flexbox/', 'https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/CSS3_logo_and_wordmark.svg/800px-CSS3_logo_and_wordmark.svg.png'),
(3, 'Introduction to Pandas', 'Pandas provides DataFrames for structured data analysis in Python.', 'https://pandas.pydata.org/docs/', 'https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/Pandas_logo.svg/800px-Pandas_logo.svg.png');

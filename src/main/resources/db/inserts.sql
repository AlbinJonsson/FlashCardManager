-- ------------------------------
-- Users
-- ------------------------------
INSERT INTO users (username, password) VALUES
('Alice', 'password123'),
('Bob', 'securepass');

-- ------------------------------
-- Tags
-- ------------------------------
-- Alice's tags
INSERT INTO tags (user_id, title, color) VALUES
(1, 'School', 'FF9900'),
(1, 'Hobby', 'CC00FF');

-- Bob's tags
INSERT INTO tags (user_id, title, color) VALUES
(2, 'Work', '00CC88'),
(2, 'Fun', 'FF00CC');

-- ------------------------------
-- Decks
-- ------------------------------
-- Alice's decks
INSERT INTO decks (title, user_id, tag_id) VALUES
('Math Deck', 1, 1),   -- tag_id 1 = Alice's "School"
('Science Deck', 1, 1); -- same tag for another deck

-- Bob's decks
INSERT INTO decks (title, user_id, tag_id) VALUES
('Programming Deck', 2, 3); -- tag_id 3 = Bob's "Work"

-- ------------------------------
-- Flashcards
-- ------------------------------
-- ------------------------------
-- Alice's Math Deck (deck_id = 1)
-- ------------------------------
INSERT INTO flashcards (front, back, deck_id) VALUES
('2 + 2', '4', 1),
('5 * 3', '15', 1),
('Square root of 16', '4', 1),
('10 / 2', '5', 1);

-- ------------------------------
-- Alice's Science Deck (deck_id = 2)
-- ------------------------------
INSERT INTO flashcards (front, back, deck_id) VALUES
('Water chemical formula', 'H2O', 2),
('Speed of light in vacuum', '299,792,458 m/s', 2),
('Planet closest to the Sun', 'Mercury', 2),
('Human body largest organ', 'Skin', 2);

-- ------------------------------
-- Bob's Programming Deck (deck_id = 3)
-- ------------------------------
INSERT INTO flashcards (front, back, deck_id) VALUES
('Java keyword for inheritance', 'extends', 3),
('Print to console in Java', 'System.out.println()', 3),
('Keyword for interface implementation', 'implements', 3),
('Java primitive type for decimals', 'double', 3);

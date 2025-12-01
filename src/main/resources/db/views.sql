-- View: user_decks
CREATE OR REPLACE VIEW user_decks AS
SELECT
    d.id AS deck_id,
    d.title AS deck_title,
    d.user_id AS user_id,
    u.username AS username,
    d.tag_id AS tag_id,
    t.title AS tag_title,
    t.color AS tag_color
FROM decks d
JOIN users u ON d.user_id = u.id
LEFT JOIN tags t ON d.tag_id = t.id;


-- View: flashcards_full
CREATE OR REPLACE VIEW flashcards_full AS
SELECT
    f.id AS flashcard_id,
    f.front AS front,
    f.back AS back,
    d.id AS deck_id,
    d.title AS deck_title,
    u.id AS user_id,
    u.username AS username,
    t.id AS tag_id,
    t.title AS tag_title,
    t.color AS tag_color
FROM flashcards f
JOIN decks d ON f.deck_id = d.id
JOIN users u ON d.user_id = u.id
LEFT JOIN tags t ON d.tag_id = t.id;  -- LEFT JOIN in case deck has no tag

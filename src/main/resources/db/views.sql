CREATE OR REPLACE VIEW UserDecks AS
SELECT
    d.id AS deck_id,
    d.title AS deck_title,
    d.user_id,
    u.username,
    d.tag_id,
    t.title AS tag_title,
    t.color AS tag_color
FROM Decks d
JOIN Users u ON d.user_id = u.id
LEFT JOIN Tags t ON d.tag_id = t.id;


CREATE OR REPLACE VIEW FlashcardsFull AS
SELECT
    f.id AS flashcard_id,
    f.front,
    f.back,
    d.id AS deck_id,
    d.title AS deck_title,
    u.id AS user_id,
    u.username,
    t.id AS tag_id,
    t.title AS tag_title,
    t.color AS tag_color
FROM FlashCards f
JOIN Decks d ON f.deck_id = d.id
JOIN Users u ON d.user_id = u.id
JOIN Tags t ON d.tag_id = t.id;

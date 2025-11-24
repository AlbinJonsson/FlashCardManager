CREATE TABLE Users (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    CHECK (char_length(username) BETWEEN 3 AND 20),
    CHECK (char_length(password) BETWEEN 3 AND 20)
);

CREATE TABLE Tags (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES Users(id) ON DELETE CASCADE,
    title TEXT NOT NULL,
    CHECK (char_length(title) BETWEEN 1 AND 20),
    color CHAR(6) NOT NULL,
    CHECK (color ~ '^[0-9A-Fa-f]{6}$'),
    UNIQUE(user_id, title)  --each user cannot have two tags with the same title
);

CREATE TABLE Decks (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title TEXT NOT NULL,
    user_id INTEGER NOT NULL REFERENCES Users(id) ON DELETE CASCADE,
    tag_id INTEGER REFERENCES Tags(id) ON DELETE SET NULL,
    UNIQUE(user_id, title),  --prevent duplicate deck names per user
    CHECK (char_length(title) BETWEEN 1 AND 20) --deck title max 20 chars
);
--Important: SQL does not enforce that tag_id belongs to the same user.
--This must be handled in Java when creating or copying decks.

CREATE TABLE FlashCards (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    front TEXT NOT NULL,
    back TEXT NOT NULL,
    deck_id INTEGER NOT NULL REFERENCES Decks(id) ON DELETE CASCADE,
    UNIQUE(deck_id, front), --prevent duplicate fronts
    CHECK (char_length(front) BETWEEN 1 AND 100), --deck title max 100 chars
    CHECK (char_length(back) BETWEEN 1 AND 100) --deck title max 100 chars
);

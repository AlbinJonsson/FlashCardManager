package org.Flashcard.models;

public class Deck {
    private int id;
    private String title;
    private int userId; // owner of the deck
    private int tagId;  // associated tag

    // Constructors
    public Deck() {}

    public Deck(int id, String title, int userId, int tagId) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.tagId = tagId;
    }

    public Deck(String title, int userId, int tagId) {
        this.title = title;
        this.userId = userId;
        this.tagId = tagId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getTagId() { return tagId; }
    public void setTagId(int tagId) { this.tagId = tagId; }

    @Override
    public String toString() {
        return "Deck{id=" + id + ", title='" + title + "', userId=" + userId + ", tagId=" + tagId + "}";
    }
}

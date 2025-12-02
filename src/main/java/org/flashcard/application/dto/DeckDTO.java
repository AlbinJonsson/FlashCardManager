package org.flashcard.application.dto;

public class DeckDTO {
    private final String title;
    private final int id;
    //private final int tagId;
    public DeckDTO(String title, int id) {
        this.title = title;
        this.id = id;
//        this.tagId = tagId;
    }
    public String getTitle() { return title; }
    public int getId() { return id; }

    //public int getTagId() { return tagId; }
}

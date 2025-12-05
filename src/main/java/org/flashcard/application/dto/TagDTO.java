package org.flashcard.application.dto;


import java.awt.*;

public class TagDTO {
    private final int id;
    private final String title;
    private final String colorHex; // T.ex. "#FF5733"

    public TagDTO(int id, String title, String colorHex) {
        this.id = id;
        this.title = title;
        this.colorHex = colorHex;
    }

    public String getTitle() { return title; }

    public Color getColor() {
        if (colorHex == null || colorHex.isEmpty()) {
            return Color.WHITE; // or a default
        }

        // Ensure the string starts with a '#'
        String hex = colorHex.startsWith("#") ? colorHex : "#" + colorHex;

        return Color.decode(hex);
    }
    public int getId() { return id; }

    @Override
    public String toString() {
        return title;   // or getUsername()
    }


}
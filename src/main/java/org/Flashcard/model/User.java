package org.Flashcard.model;

public class User {
    private Long id;
    private String name;
    private String passwordHash;

    public User(String name, String passwordHash) {
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}

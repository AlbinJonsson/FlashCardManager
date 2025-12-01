package org.flashcard.models.dataclasses;

import jakarta.persistence.*;

@Entity
@Table(name = "Tags",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "title"})}) // unique per user
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false, length = 6) // hex color
    private String color;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    // Constructors
    public Tag() {}

    public Tag(String title, String color, User user) {
        this.title = title;
        this.color = color;
        this.user = user;
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}

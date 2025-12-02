package org.flashcard.models.dataclasses;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Decks",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "title"})})
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String title;

    @Column(nullable = false)
    private LocalDate dateCreated = LocalDate.now();

    // Many decks belong to one user
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    // Optional tag
    @ManyToOne
    @JoinColumn(name = "tagId", nullable = true)
    private Tag tag;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Flashcard> cards;

    // Constructors
    public Deck() {}

    public Deck(String title, User user) {
        this.title = title;
        this.user = user;
        this.dateCreated = LocalDate.now();
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDate dateCreated) { this.dateCreated = dateCreated; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Tag getTag() { return tag; }
    public void setTag(Tag tag) { this.tag = tag; }

    public List<Flashcard> getCards() {
        return cards;
    }

    public void setCards(List<Flashcard> cards) {
        this.cards = cards;
    }
}

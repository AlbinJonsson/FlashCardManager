package org.Flashcard.model;

public class FlashCard {
    private Long id;
    private String front;
    private String back;
    private Deck deck;
    private CardLearningState learningState;

    public FlashCard(Deck deck, String front, String back) {
        this.deck = deck;
        this.front = front;
        this.back = back;
        this.learningState = CardLearningState.initial();
    }

    public Long getId() {
        return id;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }

    public Deck getDeck() {
        return deck;
    }

    public User getOwner() {
        return deck.getOwner();
    }

    public CardLearningState getLearningState() {
        return learningState;
    }
}

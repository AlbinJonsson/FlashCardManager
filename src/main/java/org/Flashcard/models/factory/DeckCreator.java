package org.Flashcard.models.factory;

import org.Flashcard.models.dataClasses.*;

public class DeckCreator extends EntityCreator {
    private final String title;
    private final User owner;
    private final Tag tag;           // can be null

    public DeckCreator(String title, User owner, Tag tag) {
        this.title = title;
        this.owner = owner;
        this.tag   = tag;
    }

    @Override
    public Deck create() {
        Deck deck = new Deck(title, owner);
        deck.setTag(tag);
        return deck;
    }
}
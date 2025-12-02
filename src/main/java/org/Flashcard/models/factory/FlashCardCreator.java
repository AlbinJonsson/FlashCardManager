// 4. FlashCardCreator.java
package org.Flashcard.models.factory;

import org.Flashcard.models.dataClasses.*;

public class FlashCardCreator extends EntityCreator {
    private final String front;
    private final String back;
    private final Deck deckId;

    public FlashCardCreator(String front, String back, Deck deck) {
        this.front = front;
        this.back  = back;
        this.deckId  = deck;
    }

    @Override
    public FlashCard create() {
        return new FlashCard(front, back, deckId;
    }
}
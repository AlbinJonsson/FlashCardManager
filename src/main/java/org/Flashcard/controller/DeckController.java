package org.Flashcard.controller;

import org.Flashcard.model.Deck;
import org.Flashcard.model.User;
import org.Flashcard.repository.IDeckRepository;

import java.util.List;

public class DeckController {

    private final IDeckRepository decks;

    public DeckController(IDeckRepository decks) {
        this.decks = decks;
    }

    public Deck createDeck(User owner, String title, String tag) {
        Deck deck = new Deck(owner, title, tag);
        return decks.save(deck);
    }

    public List<Deck> getDecksForUser(User user) {
        return decks.findByUser(user);
    }
}

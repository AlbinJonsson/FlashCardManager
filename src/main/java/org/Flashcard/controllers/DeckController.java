package org.Flashcard.controllers;

import org.Flashcard.models.factory.EntityCreator;
import org.Flashcard.models.dataClasses.Deck;
import org.Flashcard.models.dataClasses.FlashCard;
import org.Flashcard.repositories.DeckRepository;
import org.Flashcard.repositories.FlashCardRepository;

import java.sql.SQLException;
import java.util.List;

public class DeckController { // Implements DataFacade? → not required unless specified

    private final DeckRepository deckRepository;
    private final FlashCardRepository flashCardRepository;

    public DeckController(DeckRepository deckRepository, FlashCardRepository flashCardRepository) {
        this.deckRepository = deckRepository;
        this.flashCardRepository = flashCardRepository;
    }

    // Required by UML
    public Deck createDeck(String title, int tagId) throws SQLException {
        Deck deck = EntityCreator.createDeck(title, tagId);
        return deckRepository.create(deck);
    }

    public Deck editDeck(Deck deck) throws SQLException {
        // Assuming you add an update() method to DeckRepository later
        // For now, just return the same deck (or throw UnsupportedOperationException)
        // But to match UML signature:
        return deckRepository.create(deck); // or update when implemented
    }

    public boolean deleteDeck(int deckId) throws SQLException {
        return deckRepository.delete(deckId);
    }

    public void assignTag(int deckId, int tagId) throws SQLException {
        Deck deck = deckRepository.findByIdWithCards(deckId);
        if (deck != null) {
            deck.setTagId(tagId);
            // You’ll need an update() in DeckRepository eventually
        }
    }

    public FlashCard addCard(int deckId, String question, String answer) throws SQLException {
        FlashCard card = EntityCreator.createFlashCard(question, answer);
        card.setDeckId(deckId);
        return flashCardRepository.create(card);
    }

    public FlashCard editCard(FlashCard card) throws SQLException {
        // Again, will need update() in repo later
        // For now, just re-insert or return same
        return card;
    }

    public boolean deleteCard(int cardId) throws SQLException {
        return flashCardRepository.delete(cardId);
    }

    public List<FlashCard> importCards() {
        // Not implemented yet → return empty list or throw
        return List.of();
    }

    // Extra helpful ones (not strictly in UML but useful)
    public Deck findByDeckId(int deckId) throws SQLException {
        return deckRepository.findByIdWithCards(deckId);
    }
}
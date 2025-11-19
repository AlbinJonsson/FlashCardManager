package org.Flashcard.controller;

import org.Flashcard.model.Deck;
import org.Flashcard.model.FlashCard;
import org.Flashcard.repository.IFlashCardRepository;

import java.util.List;

public class FlashCardController {

    private final IFlashCardRepository cards;

    public FlashCardController(IFlashCardRepository cards) {
        this.cards = cards;
    }

    public FlashCard createFlashCard(Deck deck, String front, String back) {
        FlashCard card = new FlashCard(deck, front, back);
        return cards.save(card);
    }

    public List<FlashCard> getCardsForDeck(Deck deck) {
        return cards.findByDeck(deck);
    }
}

package org.Flashcard.repository;

import org.Flashcard.model.FlashCard;
import java.util.*;
import java.util.stream.Collectors;

import org.Flashcard.model.Deck;

// 3. FlashCardRepository
public class FlashCardRepository extends BaseRepository<FlashCard> implements IFlashCardRepository {

    @Override
    public FlashCard save(FlashCard card) {
        return setAndStore(card);
    }

    @Override
    public List<FlashCard> findByDeck(Deck deck) {
        return storage.values().stream()
                .filter(c -> c.getDeck().getId().equals(deck.getId()))
                .collect(Collectors.toList()); // <-- Fixad
    }
}

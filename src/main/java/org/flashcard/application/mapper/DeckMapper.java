package org.flashcard.application.mapper;

import org.flashcard.application.dto.DeckDTO;
import org.flashcard.models.dataclasses.Deck;

public class DeckMapper {
    public static DeckDTO toDTO(Deck deck) {
        return new DeckDTO(
                deck.getTitle(),
                deck.getId()
        );
    }
}

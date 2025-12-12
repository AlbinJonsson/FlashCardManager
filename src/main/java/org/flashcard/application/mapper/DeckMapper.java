package org.flashcard.application.mapper;

import org.flashcard.application.dto.DeckDTO;
import org.flashcard.application.dto.TagDTO;
import org.flashcard.models.dataclasses.Deck;
import org.flashcard.models.progress.DeckProgression;

import java.util.List;
//Our Mapper classes converts our data classes to DTOs
public class DeckMapper {
    public static DeckDTO toDTO(Deck deck, int dueCount) {
        int cardCount = (deck.getCards() != null) ? deck.getCards().size() : 0;
        TagDTO tagDTO = TagMapper.toDTO(deck.getTag());

        double progression = DeckProgression.calculateDeckProgression(deck);

        return new DeckDTO(
                deck.getTitle(),
                deck.getId(),
                cardCount,
                tagDTO,
                dueCount,
                progression
        );
    }
    public static DeckDTO toDTO(Deck deck) {
        return toDTO(deck, 0);
    }

    public static List<DeckDTO> toDTOList(List<Deck> decks) {
        return decks.stream().map(DeckMapper::toDTO).toList();
    }
}

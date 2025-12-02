package org.flashcard.application.mapper;

import org.flashcard.application.dto.DeckDTO;
import org.flashcard.application.dto.TagDTO;
import org.flashcard.models.dataclasses.Deck;

import java.util.List;

public class DeckMapper {
    public static DeckDTO toDTO(Deck deck) {
        int cardCount = (deck.getCards() != null) ? deck.getCards().size() : 0;
        TagDTO tagDTO = TagMapper.toDTO(deck.getTag());

        return new DeckDTO(
                deck.getTitle(),
                deck.getId(),
                cardCount,
                tagDTO
        );
    }
    public static List<DeckDTO> toDTOList(List<Deck> decks) {
        return decks.stream().map(DeckMapper::toDTO).toList();
    }
}

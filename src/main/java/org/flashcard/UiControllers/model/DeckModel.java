package org.flashcard.UiControllers.model;

import org.flashcard.controllers.DeckController;
import org.flashcard.models.dataclasses.Deck;
import org.flashcard.models.dataclasses.User;
import org.flashcard.UiControllers.Observable;
import org.flashcard.UiControllers.dto.DeckDTO;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class DeckModel extends Observable<List<DeckDTO>> {

    private final DeckController deckController;
    private User currentUser;

    public DeckModel(DeckController deckController) {
        this.deckController = deckController;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        reloadDecks();
    }

    public void reloadDecks() {
        if (currentUser == null) {
            notify(List.of());
            return;
        }

        List<Deck> decks = deckController.getDecksForUser(currentUser.getId());

        List<DeckDTO> dtoList = decks.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        notify(dtoList);
    }

    private DeckDTO toDto(Deck deck) {
        int cardCount = deck.getCards() != null ? deck.getCards().size() : 0;

        // TODO: beräkna riktiga trophies senare
        int trophyCount = 0;

        String tagTitle = null;
        Color tagColor = Color.GRAY;

        if (deck.getTag() != null) {
            tagTitle = deck.getTag().getTitle();
            String hex = deck.getTag().getColor();
            if (hex != null && !hex.isBlank()) {
                if (!hex.startsWith("#")) {
                    hex = "#" + hex;
                }
                try {
                    tagColor = Color.decode(hex);
                } catch (NumberFormatException e) {
                    tagColor = Color.GRAY;
                }
            }
        }

        return new DeckDTO(
                deck.getId(),
                deck.getTitle(),
                cardCount,
                trophyCount,
                tagTitle,
                tagColor
        );
    }
}

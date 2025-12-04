package org.flashcard.UiControllers.dto;

import java.awt.Color;

public record DeckDTO(
        Integer id,
        String title,
        int cardCount,
        int trophyCount,
        String tagTitle,
        Color tagColor
) {}

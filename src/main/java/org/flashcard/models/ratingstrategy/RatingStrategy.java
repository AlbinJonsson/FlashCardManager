package org.flashcard.models.ratingstrategy;

import org.flashcard.models.dataclasses.Flashcard;

public interface RatingStrategy {
    void updateReviewState(Flashcard flashCard);
}

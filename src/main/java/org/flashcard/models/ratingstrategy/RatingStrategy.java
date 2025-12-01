package org.flashcard.models.ratingstrategy;

import org.flashcard.models.dataclasses.Flashcard;

public interface RatingStrategy {
    int calculateNextReviewDate(Flashcard card);
    void updateReviewDate(Flashcard flashCard);
}

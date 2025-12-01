package org.flashcard.models.ratingstrategy;

import org.flashcard.models.dataclasses.Flashcard;

public final class RatingContext {

    private RatingContext() {
    }

    //Kallar på strategins metoder
    public static void executeStrategy(Flashcard flashCard, RatingStrategy ratingStrategy) {

        if (ratingStrategy == null) {
            throw new IllegalArgumentException("ratingStrategy cannot be null");
        }
        ratingStrategy.calculateNextReviewDate(flashCard);
        ratingStrategy.updateReviewDate(flashCard);
    }
}




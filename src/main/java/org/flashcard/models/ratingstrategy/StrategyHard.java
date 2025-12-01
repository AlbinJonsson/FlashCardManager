package org.flashcard.models.ratingstrategy;

import org.flashcard.models.dataclasses.Flashcard;

import java.time.LocalDate;

public class StrategyHard implements RatingStrategy {
    @Override
    public int calculateNextReviewDate(Flashcard card) {
        return 1;
    }
    public void updateReviewDate(Flashcard flashCard){
        LocalDate newReviewDate = flashCard.getCardLearningState().getLastReviewDate().plusDays(2);
        flashCard.getCardLearningState().setNextReviewDate(newReviewDate);
    }
}

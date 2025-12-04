package org.flashcard.models.ratingstrategy;

import org.flashcard.models.dataclasses.CardLearningState;
import org.flashcard.models.dataclasses.Flashcard;

public abstract class BaseIntervalStrategy implements RatingStrategy {

    @Override
    public void updateReviewState(Flashcard flashCard) {
        CardLearningState state = flashCard.getCardLearningState();

        long daysToAdd = calculateDays(state);

        state.updateDates(daysToAdd);
    }

    //Helper method
    protected long calculateDays(CardLearningState state) {
        long currentInterval = 0;
        if (state.getLastReviewDate() != null && state.getNextReviewDate() != null) {
            currentInterval = java.time.temporal.ChronoUnit.DAYS.between(state.getLastReviewDate(), state.getNextReviewDate());
        }
        if (currentInterval <= 0) currentInterval = 1;

        double multiplier = getMultiplier();
        return Math.round(currentInterval * multiplier);
    }

    protected abstract double getMultiplier();
}

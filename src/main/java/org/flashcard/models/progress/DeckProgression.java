package org.flashcard.models.progress;

import org.flashcard.models.dataclasses.CardLearningState;
import org.flashcard.models.dataclasses.Deck;
import org.flashcard.models.dataclasses.Flashcard;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DeckProgression {

    private static final int TARGET_PROGRESS_DAYS = 30;

    public static double calculateDeckProgression(Deck deck) {

        if (deck == null || Deck.getCards() == null || Deck.getCards().isEmpty()) {
            return 0.0;
        }

        List<Flashcard> cards = Deck.getCards();
        List<Long> intervals = new ArrayList<>();

        //Collects intervall for each card in cards
        for (Flashcard card : cards) {

            if (card.getCardLearningState() != null) {

                //Get and input CardLeadningState to the helper function getIntervalDays
                long intervalDays = getIntervalDays(card.getCardLearningState());
                intervals.add(intervalDays);
            }
        }

        if (intervals.isEmpty()) {
            return 0.0;
        }

        // Sort List (Needed to calculate median)
        intervals.sort(Comparator.naturalOrder());

        // Calculate median
        long medianInterval = calculateMedian(intervals);

        // Calculate Procent as median / Target_Progress_Days (which is 30d)
        double progressRatio = (double) medianInterval / TARGET_PROGRESS_DAYS;

        return Math.min(progressRatio * 100.0, 100.0);
    }

    //helper functions below:
    //Calculate the intevall for each card and returns interval in days
    public static long getIntervalDays(CardLearningState cardLearningState) {
        if (cardLearningState.getLastReviewDate() == null || cardLearningState.getNextReviewDate() == null) {
            return 0;
        }
        //Calculate the intevall for each card and returns interval in days
        return ChronoUnit.DAYS.between(cardLearningState.getLastReviewDate(), cardLearningState.getNextReviewDate());
    }

    //Calculates the median
    private static long calculateMedian(List<Long> sortedNumbers) {

        int size = sortedNumbers.size();

        if (size % 2 == 1) {
            return sortedNumbers.get(size / 2);
        } else {
            long middle1 = sortedNumbers.get(size / 2 - 1);
            long middle2 = sortedNumbers.get(size / 2);
            return Math.round((middle1 + middle2) / 2.0);
        }
    }
}
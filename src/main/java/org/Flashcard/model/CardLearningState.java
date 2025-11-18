package org.Flashcard.model;

import java.time.LocalDate;

public class CardLearningState {
    private Long id;
    private LocalDate nextReviewDate;
    private int intervalDays;
    private double easeFactor;
    private Rating lastRating;

    private CardLearningState(LocalDate nextReviewDate, int intervalDays, double easeFactor, Rating lastRating) {
        this.nextReviewDate = nextReviewDate;
        this.intervalDays = intervalDays;
        this.easeFactor = easeFactor;
        this.lastRating = lastRating;
    }

    public static CardLearningState initial() {
        return new CardLearningState(LocalDate.now(), 1, 2.5, Rating.MEDIUM);
    }

    public LocalDate getNextReviewDate() {
        return nextReviewDate;
    }

    public int getIntervalDays() {
        return intervalDays;
    }

    public double getEaseFactor() {
        return easeFactor;
    }

    public Rating getLastRating() {
        return lastRating;
    }
}

package org.flashcard.models.dataclasses;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CardLearningState")
public class CardLearningState {

    @Id
    private Integer flashcardId; // shared PK with Flashcard

    @OneToOne
    @MapsId // Use flashcardId as both PK and FK
    @JoinColumn(name = "flashcardId")
    private Flashcard flashcard;

    private LocalDate lastReviewDate;
    private LocalDate nextReviewDate;

    @Column(nullable = false)
    private Integer numberOfTimesViewed = 0;

    // Constructors
    public CardLearningState() {}

    public CardLearningState(Flashcard flashcard) {
        this.flashcard = flashcard;
        this.numberOfTimesViewed = 0;
    }

    // Getters and setters
    public Integer getFlashcardId() { return flashcardId; }
    public void setFlashcardId(Integer flashcardId) { this.flashcardId = flashcardId; }

    public Flashcard getFlashcard() { return flashcard; }
    public void setFlashcard(Flashcard flashcard) { this.flashcard = flashcard; }

    public LocalDate getLastReviewDate() { return lastReviewDate; }
    public void setLastReviewDate(LocalDate lastReviewDate) { this.lastReviewDate = lastReviewDate; }

    public LocalDate getNextReviewDate() { return nextReviewDate; }
    public void setNextReviewDate(LocalDate nextReviewDate) { this.nextReviewDate = nextReviewDate; }

    public Integer getNumberOfTimesViewed() { return numberOfTimesViewed; }
    public void setNumberOfTimesViewed(Integer numberOfTimesViewed) { this.numberOfTimesViewed = numberOfTimesViewed; }

    public boolean isDueToday() {
        return nextReviewDate == null || !nextReviewDate.isAfter(LocalDate.now());
    }

    @Override
    public String toString() {
        return "CardLearningState{flashcardId=" + flashcardId +
                ", lastReviewDate=" + lastReviewDate +
                ", nextReviewDate=" + nextReviewDate +
                ", numberOfTimesViewed=" + numberOfTimesViewed + "}";
    }
}

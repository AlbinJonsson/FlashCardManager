package org.Flashcard.model;

public class DeckProgress {
    private Long id;
    private int masteredCount;
    private int totalCount;

    public DeckProgress(int totalCount) {
        this.masteredCount = 0;
        this.totalCount = totalCount;
    }

    public int getMasteredCount() {
        return masteredCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void updateTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public double getPercentComplete() {
        if (totalCount == 0) return 0;
        return (masteredCount * 100.0) / totalCount;
    }
}

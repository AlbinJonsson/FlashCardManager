package org.Flashcard.model;

public class Deck {
    private Long id;
    private DeckProgress progress;
    private User owner;
    private String title;
    private String tag;
    private int totalCount;

    public Deck(User owner, String title, String tag) {
        this.owner = owner;
        this.title = title;
        this.tag = tag;
        this.totalCount = 0;
        this.progress = new DeckProgress(totalCount);
    }

    @Override
    public String toString() {
        return title + " (" + totalCount + " cards)";
    }

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public String getTag() {
        return tag;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void updateTotalCount(int totalCount){
        this.progress.updateTotalCount(totalCount);
    }

    private void incrementTotalCount(){
        this.totalCount++;
        this.progress.updateTotalCount(this.totalCount);
    }

    public FlashCard createFlashCard(String front, String back) {
        FlashCard card = new FlashCard(this, front, back);
        this.incrementTotalCount();
        return card;
    }
}

package org.flashcard.application.dto;
// We use DataTransferObjects to transfer data between layers.
// This ensures View is read-only.
public class DeckDTO {
    private final String title;
    private final int id;
    private final int cardCount;
    private final TagDTO tagDTO;
    private final int dueCount;
    private final double progression;

    public DeckDTO(String title, int id, int cardCount, TagDTO tagDTO, int dueCount, double progression) {
        this.title = title;
        this.id = id;
        this.cardCount = cardCount;
        this.tagDTO = tagDTO;
        this.dueCount = dueCount;
        this.progression = progression;
    }
    public String getTitle() { return title; }
    public int getId() { return id; }
    public int getCardCount() { return cardCount; }
    public TagDTO getTagDTO() { return tagDTO; }
    public int getDueCount() { return dueCount; }
    public double getProgression() { return progression; }
}

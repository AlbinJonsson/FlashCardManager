package org.Flashcard.repository;
import org.Flashcard.model.FlashCard;
import org.Flashcard.model.Deck;
import java.util.List;

public interface IFlashCardRepository extends IRepository<FlashCard> {
    FlashCard save(FlashCard card);
    List<FlashCard> findByDeck(Deck deck);
}
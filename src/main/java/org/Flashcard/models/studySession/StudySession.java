package org.Flashcard.models.studySession;

import org.Flashcard.models.dataClasses.Deck;
import org.Flashcard.models.dataClasses.FlashCard;
import org.Flashcard.models.dataClasses.User;

import java.util.List;

public class StudySession {
    private List<FlashCard> cards;

    private final Deck deck;
    private final User user;

    private final StudyAlgorithm studyAlgorithm;

    public StudySession(Deck deck, User user, StudyAlgorithm studyAlgorithm) {

        this.studyAlgorithm = studyAlgorithm;

        this.deck = deck;
        this.user = user;
    }

    private void startSession(){
        cards = studyAlgorithm.prepareCards(deck, user);
    }

    public FlashCard getNextCardAndRemove() {
        if (this.cards.isEmpty()) {
            return null;
        }
        FlashCard currentCard = cards.get(0);
        return cards.remove(0);
    }
}

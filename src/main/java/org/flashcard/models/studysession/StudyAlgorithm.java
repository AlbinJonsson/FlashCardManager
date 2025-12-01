package org.flashcard.models.studysession;

import org.flashcard.models.dataclasses.Deck;
import org.flashcard.models.dataclasses.Flashcard;
import org.flashcard.models.dataclasses.User;

import java.util.List;

public interface StudyAlgorithm {
    public List<Flashcard> prepareCards(Deck deck, User user);
}

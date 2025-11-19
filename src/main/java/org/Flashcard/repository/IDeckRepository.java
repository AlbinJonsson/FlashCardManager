package org.Flashcard.repository;
import org.Flashcard.model.Deck;
import org.Flashcard.model.User;
import java.util.*;


public interface IDeckRepository extends IRepository<Deck> {
    Deck save(Deck deck);
    List<Deck> findByUser(User user);
}


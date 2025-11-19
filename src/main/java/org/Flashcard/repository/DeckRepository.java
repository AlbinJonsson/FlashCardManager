package org.Flashcard.repository;
import java.util.*;
import org.Flashcard.model.Deck;
import org.Flashcard.model.User;
import java.util.stream.Collectors;

public class DeckRepository extends BaseRepository<Deck> implements IDeckRepository {
    @Override
    public Deck save(Deck deck) {
        return setAndStore(deck);
    }

    @Override
    public List<Deck> findByUser(User user) {
        return storage.values().stream()
                .filter(d -> d.getOwner().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }
}

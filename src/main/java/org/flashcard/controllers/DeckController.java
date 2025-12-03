package org.flashcard.controllers;

import org.flashcard.models.dataclasses.Deck;
import org.flashcard.models.dataclasses.Flashcard;
import org.flashcard.models.dataclasses.User;
import org.flashcard.models.dataclasses.Tag;
import org.flashcard.repositories.DeckRepository;
import org.flashcard.repositories.FlashcardRepository;
import org.flashcard.repositories.TagRepository;
import org.flashcard.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeckController {

    private final DeckRepository deckRepo;
    private final FlashcardRepository flashcardRepo;
    private final UserRepository userRepo;
    private final TagRepository tagRepo;

    public DeckController(DeckRepository deckRepo,
                       FlashcardRepository flashcardRepo,
                       UserRepository userRepo,
                       TagRepository tagRepo) {
        this.deckRepo = deckRepo;
        this.flashcardRepo = flashcardRepo;
        this.userRepo = userRepo;
        this.tagRepo = tagRepo;
    }

    // --- Deck CRUD ---

    public Deck createDeck(Integer userId, String title, Integer tagId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Tag tag = null;
        if (tagId != null) {
            tag = tagRepo.findById(tagId)
                    .orElseThrow(() -> new IllegalArgumentException("Tag not found"));
        }

        Deck deck = new Deck(title, user);
        deck.setTag(tag);

        return deckRepo.save(deck);
    }

    public List<Deck> getDecksForUser(Integer userId) {
        return deckRepo.findByUserId(userId);
    }

    public Deck getDeckById(Integer deckId) {
        return deckRepo.findById(deckId)
                .orElseThrow(() -> new IllegalArgumentException("Deck not found"));
    }

    public Deck updateDeck(Integer deckId, String newTitle, Integer newTagId) {
        Deck deck = getDeckById(deckId);

        if (newTitle != null && !newTitle.isBlank()) deck.setTitle(newTitle);

        if (newTagId != null) {
            Tag tag = tagRepo.findById(newTagId)
                    .orElseThrow(() -> new IllegalArgumentException("Tag not found"));
            deck.setTag(tag);
        } else {
            deck.setTag(null); // allow removing tag
        }

        return deckRepo.save(deck);
    }

    public void deleteDeck(Integer deckId) {
        if (!deckRepo.existsById(deckId)) {
            throw new IllegalArgumentException("Deck not found");
        }
        deckRepo.deleteById(deckId);
    }

    // --- Flashcard CRUD ---

    public Flashcard addFlashcard(Integer deckId, String front, String back) {
        Deck deck = getDeckById(deckId);
        Flashcard card = new Flashcard(front, back, deck);

        // Optional: initialize learning state
        card.setCardLearningState(new org.flashcard.models.dataclasses.CardLearningState(card));

        return flashcardRepo.save(card);
    }

    public List<Flashcard> getFlashcardsForDeck(Integer deckId) {
        return flashcardRepo.findByDeckId(deckId);
    }

    public Flashcard updateFlashcard(Integer cardId, String newFront, String newBack) {
        Flashcard card = flashcardRepo.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Flashcard not found"));

        if (newFront != null && !newFront.isBlank()) card.setFront(newFront);
        if (newBack != null && !newBack.isBlank()) card.setBack(newBack);

        return flashcardRepo.save(card);
    }

    public void deleteFlashcard(Integer cardId) {
        if (!flashcardRepo.existsById(cardId)) {
            throw new IllegalArgumentException("Flashcard not found");
        }
        flashcardRepo.deleteById(cardId);
    }

    public List<String> searchDecks(Integer userId, String query) {
        if (query == null || query.isBlank()) {
            return getDecksForUser(userId)
                    .stream()
                    .map(Deck::getTitle)
                    .toList();
        }

        String lower = query.toLowerCase();

        return getDecksForUser(userId)
                .stream()
                .filter(deck -> deck.getTitle().toLowerCase().contains(lower))
                .map(Deck::getTitle)
                .toList();
    }


}

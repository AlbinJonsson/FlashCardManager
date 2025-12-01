package org.flashcard.controllers;

import org.flashcard.models.dataclasses.Deck;
import org.flashcard.models.dataclasses.Flashcard;
import org.flashcard.models.dataclasses.User;
import org.flashcard.models.ratingstrategy.RatingContext;
import org.flashcard.models.ratingstrategy.StrategyFactory;
import org.flashcard.models.studysession.StudyAlgorithm;
import org.flashcard.models.studysession.StudyAlgorithmFactory;
import org.flashcard.models.studysession.StudySession;
import org.flashcard.repositories.DeckRepository;
import org.flashcard.repositories.FlashcardRepository;
import org.flashcard.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Controller
@Transactional
public class StudyController {

    private final FlashcardRepository flashcardRepository;
    private final DeckRepository deckRepository;
    private final UserRepository userRepository;

    private StudySession currentSession;

    public StudyController(FlashcardRepository flashcardRepository,
                           DeckRepository deckRepository,
                           UserRepository userRepository) {
        this.flashcardRepository = flashcardRepository;
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
    }

    // Start a study session for a given deck and user
    public void startSession(String algorithmStrategy, int deckId, int userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Deck currentDeck = deckRepository.findById(deckId)
                .orElseThrow(() -> new IllegalArgumentException("Deck not found"));

        // Load flashcards eagerly if needed
        List<Flashcard> cards = flashcardRepository.findByDeck(currentDeck);

        StudyAlgorithm algorithm = StudyAlgorithmFactory.createAlgorithm(algorithmStrategy.toLowerCase());
        currentSession = new StudySession(currentDeck, currentUser, algorithm);
        currentSession.startSession();
    }

    // Apply a rating to a specific card in the current session
    public void applyRating(String rating, int cardId) {
        Flashcard flashcard = flashcardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("Flashcard not found"));

        RatingContext.executeStrategy(flashcard, StrategyFactory.createStrategy(rating));
        flashcardRepository.save(flashcard); // persist rating changes
    }

}

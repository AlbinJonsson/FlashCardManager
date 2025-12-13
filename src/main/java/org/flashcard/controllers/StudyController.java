package org.flashcard.controllers;

import org.flashcard.application.dto.DeckDTO;
import org.flashcard.application.dto.FlashcardDTO;
import org.flashcard.models.services.StudyService;
import org.springframework.stereotype.Controller;
import org.flashcard.controllers.observer.Observable;

@Controller
public class StudyController {

    private final StudyService studyService;

    private final Observable<FlashcardDTO> currentCardObservable = new Observable<>();
    private final Observable<Boolean> sessionFinishedObservable = new Observable<>();
    private final Observable<DeckDTO> deckProgressObservable = new Observable<>();

    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    public Observable<FlashcardDTO> getCurrentCardObservable() {
        return currentCardObservable;
    }

    public Observable<Boolean> getSessionFinishedObservable() {
        return sessionFinishedObservable;
    }

    public Observable<DeckDTO> getDeckProgressObservable() {
        return deckProgressObservable;
    }

    public void startSession(String algorithmStrategy, int deckId, int userId) {
        studyService.startSession(algorithmStrategy, deckId, userId);

        FlashcardDTO first = studyService.nextCard();
        if (first != null) {
            currentCardObservable.notifyListeners(first);
        } else {
            sessionFinishedObservable.notifyListeners(true);
        }
    }

    public void applyRating(String rating, int cardId) {
        studyService.applyRating(rating, cardId);
    }

    public void nextCardAndNotify() {
        FlashcardDTO next = studyService.nextCard();
        if (next != null) {
            currentCardObservable.notifyListeners(next);
        } else {
            endSession(true);
            sessionFinishedObservable.notifyListeners(true);
        }
    }

    public void endSession(boolean notifyView) {
        studyService.endSession();
        if (notifyView) {
            DeckDTO updated = studyService.getDeckProgress();
            if (updated != null) deckProgressObservable.notifyListeners(updated);
        }
    }
}

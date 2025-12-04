package org.flashcard.UiControllers;

import org.flashcard.controllers.StudyController;

public class StudyUIController {

    private final StudyController backend;

    public StudyUIController(StudyController backend) {
        this.backend = backend;
    }

    public void startSession(String algorithm, int deckId, int userId) {
        backend.startSession(algorithm, deckId, userId);
    }

    public void applyRating(String rating, int cardId) {
        backend.applyRating(rating, cardId);
    }

    // TODO: om du senare exponerar nästa kort från StudyController/StudySession
    // kan vi lägga till UI-metoder här för att hämta aktuell fråga/svar etc.
}

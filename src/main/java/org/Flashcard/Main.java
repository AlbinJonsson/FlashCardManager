package org.Flashcard;

import org.Flashcard.controllers.DeckController;
import org.Flashcard.controllers.StudyController;
import org.Flashcard.controllers.UserController;
import org.Flashcard.models.dataClasses.CardLearningState;
import org.Flashcard.repositories.*;
import org.Flashcard.view.AppWindow;
import view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            //Create repositories
            UserRepository userRepo = new UserRepository();
            DeckRepository deckRepo = new DeckRepository();
            TagRepository tagRepo = new TagRepository();
            FlashCardRepository flashCardRepo = new FlashCardRepository();


            //Create controllers
            final UserController userController = new UserController(userRepo,
                    deckRepo,
                    tagRepo);

            final StudyController studyController = new StudyController(
                    flashCardRepo,
                    deckRepo,
                    userRepo);
            final DeckController deckController = new DeckController();


            MainFrame frame = new MainFrame(userController,studyController,deckController);
            frame.setVisible(true);

        });
    }
}

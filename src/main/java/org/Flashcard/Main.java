package org.Flashcard;


import org.Flashcard.model.User;
import org.Flashcard.repository.*;
import org.Flashcard.controller.*;
import org.Flashcard.view.HomeView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // --- REPOSITORIES ---
        IUserRepository userRepo = new UserRepository();
        IDeckRepository deckRepo = new DeckRepository();
        IFlashCardRepository cardRepo = new FlashCardRepository();

        // --- CONTROLLERS ---
        UserController userController = new UserController(userRepo);
        DeckController deckController = new DeckController(deckRepo);
        FlashCardController flashCardController = new FlashCardController(cardRepo);

        // --- DEMO USER ---
        User currentUser = userController.register("demoUser", "123");

        // --- EXEMPEL-DECKS ---
        deckController.createDeck(currentUser, "Java Basics", "Programming");
        deckController.createDeck(currentUser, "Math", "Calculus");

        // --- STARTA GUI ---
        SwingUtilities.invokeLater(() -> {

            // Skapa parent frame först
            JFrame frame = new JFrame("Flashcards - Home");

            // Skapa HomeView med parent-frame som första argument
            HomeView homeView = new HomeView(frame, deckController, currentUser);
            frame.setContentPane(homeView.getRootPanel());

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
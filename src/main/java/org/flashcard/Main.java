package org.flashcard;

import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.StudyController;
import org.flashcard.controllers.UserController;
import org.flashcard.testview.AppFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        // Starta Spring Boot med headless mode AVSTÄNGT
        ApplicationContext context = new SpringApplicationBuilder(Main.class)
                .headless(false) // VIKTIGT: Tillåter GUI (Swing) att starta
                .run(args);

        // Hämta Controllers (bönor) som Spring har skapat
        UserController userController = context.getBean(UserController.class);
        DeckController deckController = context.getBean(DeckController.class);
        StudyController studyController = context.getBean(StudyController.class);

        // Starta Swing UI (Frontend) på Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Skicka controllerna till huvudfönstret
            AppFrame frame = new AppFrame(userController, studyController, deckController);
            frame.setVisible(true);
        });
    }
}
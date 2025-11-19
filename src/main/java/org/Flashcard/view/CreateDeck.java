package org.Flashcard.view;

import org.Flashcard.controller.DeckController;
import org.Flashcard.model.User;
import org.Flashcard.controller.*;

import javax.swing.*;

public class CreateDeck extends JDialog {
    private JPanel contentPanel;
    private JTextField titleField;
    private JTextField tagField;
    private JButton createButton;
    private JButton cancelButton;

    private final DeckController deckController;
    private final User currentUser;
    private final Runnable onDeckCreated; // callback till HomeView

    public CreateDeck(JFrame owner,
                            DeckController deckController,
                            User currentUser,
                            Runnable onDeckCreated) {
        super(owner, "Create Deck", true); // modal dialog
        this.deckController = deckController;
        this.currentUser = currentUser;
        this.onDeckCreated = onDeckCreated;

        setContentPane(contentPanel);
        getRootPane().setDefaultButton(createButton);
        pack();
        setLocationRelativeTo(owner);

        initListeners();
    }

    private void initListeners() {
        createButton.addActionListener(e -> onCreate());
        cancelButton.addActionListener(e -> dispose());
    }

    private void onCreate() {
        String title = titleField.getText().trim();
        String tag = tagField.getText().trim();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Title cannot be empty",
                    "Validation error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Skapa deck via CONTROLLER (inte View → Repo)
        deckController.createDeck(currentUser, title, tag);

        // Låt HomeView uppdatera listan
        if (onDeckCreated != null) {
            onDeckCreated.run();
        }

        dispose();
    }

    public void showDialog() {
        setVisible(true);
    }
}

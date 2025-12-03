package view;

import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.UserController;
import org.flashcard.models.dataclasses.Deck;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MyDecksView extends HomeView {

    private JButton editButton;
    private final UserController userController;
    private final DeckController deckController;

    public MyDecksView(UserController userController, DeckController deckController) {
        super();  // bygger HomeView

        this.userController = userController;
        this.deckController = deckController;

        editButton = new JButton("Edit Decks");

        addEditButton();
        styleButton();
    }

    private void addEditButton() {

        JPanel editPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        editPanel.setOpaque(false);

        editButton.setPreferredSize(new Dimension(130, 35));
        editPanel.add(editButton);

        headerPanel.add(editPanel, BorderLayout.EAST);

        headerPanel.revalidate();
        headerPanel.repaint();
    }

    private void styleButton() {
        editButton.setFocusPainted(false);
        editButton.setBackground(Color.WHITE);
        editButton.setForeground(Color.BLACK);
        editButton.setOpaque(true);
    }

    public void refreshDecksForCurrentUser() {
        var currentUser = userController.getCurrentUser();
        if (currentUser == null) {
            setDecks(List.of());
            return;
        }

        List<Deck> decks = deckController.getDecksForUser(currentUser.getId());

        List<String> names = decks.stream()
                .map(Deck::getTitle)
                .collect(Collectors.toList());

        setDecks(names);
    }
}

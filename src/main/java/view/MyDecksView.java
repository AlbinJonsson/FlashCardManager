package view;

import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.UserController;
import org.flashcard.models.dataclasses.Deck;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MyDecksView extends HomeView {

    private final UserController userController;
    private final DeckController deckController;
    private JButton editButton;

    public MyDecksView(UserController userController, DeckController deckController) {
        super();  // bygger headerPanel + titleLabel + scroll

        this.userController = userController;
        this.deckController = deckController;

        // ändra rubriken i headern
        titleLabel.setText("My Decks");

        // skapa knappen och lägg den i headerPanel, till höger
        initEditButton();
    }

    private void initEditButton() {
        editButton = new JButton("Edit decks");
        editButton.setFocusPainted(false);
        editButton.setOpaque(true);
        editButton.setBackground(Color.WHITE);
        editButton.setForeground(Color.BLACK);
        editButton.setFont(Theme.NORMAL);
        editButton.setPreferredSize(new Dimension(120, 32));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rightPanel.setOpaque(false);
        rightPanel.add(editButton);

        // **Samma headerPanel som titelLabel**
        headerPanel.add(rightPanel, BorderLayout.EAST);

        headerPanel.revalidate();
        headerPanel.repaint();
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

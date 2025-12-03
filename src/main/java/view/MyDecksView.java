package view;

import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.UserController;
import org.flashcard.models.Observer;
import org.flashcard.models.dataclasses.Deck;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MyDecksView extends HomeView implements Observer {

    private JButton editButton;
    private final UserController userController;
    private final DeckController deckController;
    private JButton addDeckButton;
    private ButtonListener listener;
    private CreateDeckView createDeckView;
    public MyDecksView(UserController userController, DeckController deckController) {
        super();
        this.userController = userController;
        this.deckController = deckController;

        editButton = new JButton("Edit Decks");
        addDeckButton = new JButton("Add New Deck");
        addExtras();
        styleExtras();
        addListeners();
    }
    public void setButtonListener(ButtonListener listener) {
        this.listener = listener;
    }

    private void addExtras() {
        titleLabel.setText("My Decks");

        JPanel editPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        editPanel.setOpaque(false);
        editPanel.add(editButton);
        editPanel.add(addDeckButton);

        headerPanel.add(editPanel, BorderLayout.EAST);
        headerPanel.setBackground(Color.WHITE);
    }
    private void addListeners() {
        addDeckButton.addActionListener(e -> {

            listener.buttonPressed("CreateDeck");

        });
        editButton.addActionListener(e -> listener.buttonPressed("MyDecks"));
    }
    private void styleExtras() {
        editButton.setBackground(new Color(230, 230, 230));
        editButton.setForeground(Color.BLACK);
        editButton.setFocusPainted(false);
        addDeckButton.setBackground(new Color(230, 230, 230));
        addDeckButton.setForeground(Color.BLACK);
        addDeckButton.setFocusPainted(false);
    }

    // Refresh decks for current user
    public void refreshDecksForCurrentUser() {
        var currentUser = userController.getCurrentUser();
        if (currentUser == null) {
            setDecks(List.of());
            return;
        }

        List<Deck> decks = deckController.getDecksForUser(currentUser.getId());

        // Convert to simple strings
        List<String> deckNames = decks.stream()
                .map(Deck::getTitle)
                .collect(Collectors.toList());

        setDecks(deckNames);

        System.out.println("Decks for user " + currentUser.getUsername() + ": " + deckNames);
    }


    @Override
    public void update(Object data) {

    }
}

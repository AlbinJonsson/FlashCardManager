package org.Flashcard.view;

import org.Flashcard.controller.DeckController;
import org.Flashcard.model.Deck;
import org.Flashcard.model.User;

import javax.swing.*;
import java.util.List;

public class HomeView {
    private JPanel rootPanel;
    private JList<Deck> deckList;
    private JButton createDeckButton;

    private final DeckController deckController;
    private final User currentUser;
    private final JFrame parentFrame; // för att äga dialogen

    public HomeView(JFrame parentFrame, DeckController deckController, User currentUser) {
        this.parentFrame = parentFrame;
        this.deckController = deckController;
        this.currentUser = currentUser;

        loadDecks();

        createDeckButton.addActionListener(e -> openCreateDeckDialog());
    }

    private void loadDecks() {
        List<Deck> decks = deckController.getDecksForUser(currentUser);
        deckList.setListData(decks.toArray(new Deck[0]));
    }

    private void openCreateDeckDialog() {
        CreateDeck dialog =
                new CreateDeck(parentFrame, deckController, currentUser, this::loadDecks);
        dialog.showDialog();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}

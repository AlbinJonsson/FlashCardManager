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
        editButton = new JButton("Edit Decks");
        editButton.setFocusPainted(false);
        editButton.setOpaque(true);

        // --- STYLE ---
        editButton.setBackground(new Color(245, 245, 245)); // mjukare vit / light grey
        editButton.setForeground(Color.BLACK);
        editButton.setFont(Theme.NORMAL);

        editButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200), 1, true),  // rundad lätt kant
                BorderFactory.createEmptyBorder(6, 14, 6, 14)  // padding
        ));

        editButton.setPreferredSize(new Dimension(130, 36)); // lite större & elegantare

        // --- HOVER EFFEKT ---
        editButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                editButton.setBackground(new Color(235, 235, 235));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                editButton.setBackground(new Color(245, 245, 245));
            }
        });

        // Right panel (din kod)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rightPanel.setOpaque(false);
        rightPanel.add(editButton);

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

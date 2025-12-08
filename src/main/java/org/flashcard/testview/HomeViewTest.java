package org.flashcard.testview;


import org.flashcard.application.dto.DeckDTO;
import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.UserController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HomeViewTest extends JPanel {

    private final DeckController deckController;
    private final UserController userController;
    private final AppFrame appFrame; // För navigering

    private JPanel gridPanel;

    public HomeViewTest(DeckController deckController, UserController userController, AppFrame appFrame) {
        this.deckController = deckController;
        this.userController = userController;
        this.appFrame = appFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Header
        JLabel title = new JLabel("Dags att plugga!");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        add(title, BorderLayout.NORTH);

        // Grid Panel för korten
        gridPanel = new JPanel(new GridLayout(0, 3, 20, 20)); // 3 kolumner, auto rader
        gridPanel.setBackground(new Color(245, 245, 245));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Lägg i ScrollPane ifall man har många lekar
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void refreshData() {
        gridPanel.removeAll();

        Integer userId = userController.getCurrentUserId();
        if (userId == null) return;

        // 1. Hämta DTO:er med studiestatus
        // Vi antar att du har implementerat 'getDueDecksForUser' i DeckController enligt tidigare diskussion.
        // Om inte, använd 'getDecksWithStudyInfo' och filtrera här.
        List<DeckDTO> dueDecks = deckController.getDueDecksForUser(userId);

        if (dueDecks.isEmpty()) {
            JLabel emptyLabel = new JLabel("Inga kort att plugga idag! Bra jobbat.");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            gridPanel.add(emptyLabel);
        } else {
            for (DeckDTO deck : dueDecks) {
                // Visa bara decks som har kort att plugga (om metoden returnerar alla)
                if (deck.getDueCount() > 0) {
                    DeckCard card = new DeckCard(deck, e -> appFrame.startStudySession(deck.getId(), "today"));
                    gridPanel.add(card);
                }
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }
}
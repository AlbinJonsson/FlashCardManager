package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyDecksView extends HomeView {

    private JButton editButton;
    private JButton createButton;

    // Callback som MainFrame kopplar till Create Deck-logiken
    private Runnable onCreateDeck;

    public MyDecksView() {
        super();  // Bygger headerPanel, titleLabel, decksPanel, scrollPane

        titleLabel.setText("My Decks");

        initButtons();
        layoutButtons();
    }

    private void initButtons() {
        editButton = new JButton("Edit Deck");
        createButton = new JButton("Create Deck");

        styleButton(editButton);
        styleButton(createButton);

        // ---- Gör knapparna lika stora ----
        int maxWidth = Math.max(
                editButton.getPreferredSize().width,
                createButton.getPreferredSize().width
        );

        Dimension uniformSize = new Dimension(maxWidth + 20, 40);

        editButton.setPreferredSize(uniformSize);
        createButton.setPreferredSize(uniformSize);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setFocusPainted(false);

        // padding
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        // färg enligt ditt Theme
        button.setBackground(Theme.Butten_BG);
        button.setForeground(Color.WHITE);

        // muspekare
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void layoutButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setOpaque(false);

        buttonPanel.add(editButton);
        buttonPanel.add(createButton);

        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // ---- Koppla Create Deck-knappen till callback ----
        createButton.addActionListener(e -> {
            if (onCreateDeck != null) {
                onCreateDeck.run();
            }
        });
    }

    /**
     * Uppdaterar lista med deck-namn i panelen.
     * HomeView.setDecks(...) gör jobbet.
     */
    public void updateDecks(List<String> deckNames) {
        setDecks(deckNames);
    }

    /**
     * MainFrame kopplar vad som ska hända vid "Create Deck".
     */
    public void setOnCreateDeck(Runnable onCreateDeck) {
        this.onCreateDeck = onCreateDeck;
    }
}

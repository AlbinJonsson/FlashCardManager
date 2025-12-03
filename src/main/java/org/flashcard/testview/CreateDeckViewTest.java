package org.flashcard.testview;


import org.flashcard.application.dto.DeckDTO;
import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.UserController;

import javax.swing.*;
import java.awt.*;

public class CreateDeckViewTest extends JPanel {

    private final DeckController deckController;
    private final UserController userController;
    private final AppFrame appFrame;

    // State
    private DeckDTO createdDeck = null;

    // UI Components
    private JPanel formPanel;
    private JTextField titleField;
    private JTextField tagField;
    private JTextField frontField;
    private JTextField backField;

    private JButton mainActionButton; // "Create Deck" eller "Add Card"
    private JButton finishButton;
    private JLabel statusLabel;
    private JLabel headerLabel;

    public CreateDeckViewTest(DeckController deckController, UserController userController, AppFrame appFrame) {
        this.deckController = deckController;
        this.userController = userController;
        this.appFrame = appFrame;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        initComponents();
    }

    private void initComponents() {
        // --- Header ---
        headerLabel = new JLabel("Skapa ny lek");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(headerLabel, BorderLayout.NORTH);

        // --- Center Form ---
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));

        // Deck Fields (Visas först)
        titleField = createLabeledField("Titel på leken:", formPanel);
        tagField = createLabeledField("Tagg (ex. Matte, Engelska):", formPanel);

        // Card Fields (Dolda först)
        frontField = createLabeledField("Framsida (Fråga):", formPanel);
        backField = createLabeledField("Baksida (Svar):", formPanel);
        toggleCardFields(false);

        // --- Buttons ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        mainActionButton = new JButton("Skapa Lek");
        mainActionButton.setPreferredSize(new Dimension(150, 40));
        mainActionButton.setBackground(new Color(65, 105, 225));
        mainActionButton.setForeground(Color.WHITE);
        mainActionButton.addActionListener(e -> handleMainAction());

        finishButton = new JButton("Klar (Tillbaka)");
        finishButton.setPreferredSize(new Dimension(150, 40));
        finishButton.addActionListener(e -> resetAndGoBack());
        finishButton.setVisible(false); // Visas först när leken är skapad

        buttonPanel.add(mainActionButton);
        buttonPanel.add(finishButton);

        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(buttonPanel);

        statusLabel = new JLabel(" ");
        statusLabel.setForeground(new Color(0, 150, 0));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(statusLabel);

        add(formPanel, BorderLayout.CENTER);
    }

    private JTextField createLabeledField(String labelText, JPanel parent) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setMaximumSize(new Dimension(500, 60));
        p.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JTextField tf = new JTextField();
        tf.setFont(new Font("SansSerif", Font.PLAIN, 16));

        p.add(lbl, BorderLayout.NORTH);
        p.add(tf, BorderLayout.CENTER);

        parent.add(p);
        parent.add(Box.createVerticalStrut(15));
        return tf;
    }

    private void toggleCardFields(boolean show) {
        frontField.getParent().setVisible(show);
        backField.getParent().setVisible(show);

        titleField.getParent().setVisible(!show);
        tagField.getParent().setVisible(!show);

        titleField.setEditable(!show);
        tagField.setEditable(!show);
    }

    private void handleMainAction() {
        if (createdDeck == null) {
            createDeck();
        } else {
            addCard();
        }
    }

    private void createDeck() {
        String title = titleField.getText();
        String tagName = tagField.getText();
        Integer userId = userController.getCurrentUserId();

        if (title.isBlank()) {
            JOptionPane.showMessageDialog(this, "Du måste ange en titel!");
            return;
        }

        try {
            // Anropa Controller (Den uppdaterade metoden som hanterar tagg-namn)
            createdDeck = deckController.createDeck(userId, title, 1); // Default färg

            // Uppdatera UI för att lägga till kort
            headerLabel.setText("Lägg till kort i: " + createdDeck.getTitle());
            mainActionButton.setText("Lägg till Kort");
            finishButton.setVisible(true);
            toggleCardFields(true);
            statusLabel.setText("Lek skapad! Lägg nu till kort.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Fel: " + e.getMessage());
        }
    }

    private void addCard() {
        String front = frontField.getText();
        String back = backField.getText();

        if (front.isBlank() || back.isBlank()) {
            JOptionPane.showMessageDialog(this, "Både framsida och baksida behövs.");
            return;
        }

        try {
            deckController.addFlashcard(createdDeck.getId(), front, back);

            // Rensa fält och ge feedback
            frontField.setText("");
            backField.setText("");
            frontField.requestFocus();
            statusLabel.setText("Kort tillagt! (" + (createdDeck.getCardCount() + 1) + " totalt)"); // (Enkelt hack för räknare)

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Kunde inte spara kort: " + e.getMessage());
        }
    }

    private void resetAndGoBack() {
        // Nollställ formuläret
        createdDeck = null;
        titleField.setText("");
        tagField.setText("");
        frontField.setText("");
        backField.setText("");
        statusLabel.setText(" ");

        headerLabel.setText("Skapa ny lek");
        mainActionButton.setText("Skapa Lek");
        finishButton.setVisible(false);
        toggleCardFields(false);

        // Gå till MyDecks
        appFrame.navigateTo("MyDecks");
    }
}

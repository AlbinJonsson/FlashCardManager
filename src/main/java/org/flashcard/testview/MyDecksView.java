package org.flashcard.testview;

import org.flashcard.application.dto.DeckDTO;
import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.UserController;
import org.flashcard.controllers.observer.Observer;
import org.flashcard.controllers.observer.SearchQueryObservable;
import org.flashcard.models.dataclasses.Deck;

import javax.lang.model.type.DeclaredType;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyDecksView extends JPanel implements Observer<List<DeckDTO>> {

    private final DeckController deckController;
    private final UserController userController;
    private final AppFrame appFrame;
    private String searchQuery = "";
    private List<DeckDTO> currentDecks;


    private JPanel gridPanel;

    public MyDecksView(DeckController deckController, UserController userController, AppFrame appFrame) {
        this.deckController = deckController;
        this.userController = userController;
        this.appFrame = appFrame;
        Integer userId = userController.getCurrentUserId();
        currentDecks = deckController.getAllDecksForUser(userId);

        // REGISTRERA OBSERVER
        deckController.getDecksObservable().addListener(this);
        deckController.getQueryObservable().addListener(this::updateQuery);

        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Header Panel (Titel + Create Button)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 245));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        JLabel title = new JLabel("My Decks");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));

        JButton createButton = new JButton("+ Create New Deck");
        createButton.setBackground(new Color(46, 204, 113)); // Grön
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        createButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        createButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createButton.addActionListener(e -> {
            appFrame.getCreateDeckView().resetFormForNewDeck();
            appFrame.navigateTo("CreateDeck");
        });

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(createButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // --- Grid Panel ---
        gridPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        gridPanel.setBackground(new Color(245, 245, 245));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    public List<DeckDTO> filteredDecks(String searchQuery) {


        List<DeckDTO> filteredDecks = new ArrayList<DeckDTO>();
        if (currentDecks == null){
            return new ArrayList<>();
        }
        if (searchQuery == null || searchQuery.isEmpty()) {
            return currentDecks;
        }

        for (DeckDTO deckDTO : currentDecks)
            if (deckDTO.getTitle().toLowerCase().startsWith(searchQuery.toLowerCase())) {
                filteredDecks.add(deckDTO);
            }
        return filteredDecks;
    }

    public void refreshData() {
        gridPanel.removeAll();
        List<DeckDTO> updatedDecks = currentDecks;
        if (filteredDecks(searchQuery) != null && !searchQuery.equals("Search...")){

            updatedDecks = filteredDecks(searchQuery);
        }

        if (updatedDecks.isEmpty()) {
            JLabel emptyLabel = new JLabel("You don’t have any decks yet. Create one!");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            gridPanel.add(emptyLabel);
        } else {
            for (DeckDTO deck : updatedDecks) {

                JPanel wrapper = new JPanel();
                wrapper.setLayout(new BorderLayout());
                wrapper.setOpaque(false);

                DeckCard card = new DeckCard(deck, e -> appFrame.startStudySession(deck.getId(), "all"));
                wrapper.add(card, BorderLayout.CENTER);

                JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
                btnRow.setBackground(new Color(245, 245, 245));

                JButton editBtn = new JButton("Edit");
                editBtn.setPreferredSize(new Dimension(90, 28));
                editBtn.setBackground(new Color(70, 130, 180));
                editBtn.setForeground(Color.WHITE);
                editBtn.setFocusPainted(false);
                editBtn.addActionListener(e -> {
                    try {
                        appFrame.getEditDeckView().loadDeck(deck.getId());
                        appFrame.navigateTo("EditDeck");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Couldn't open edit view " + ex.getMessage());
                    }
                });

                btnRow.add(editBtn);
                wrapper.add(btnRow, BorderLayout.SOUTH);
                gridPanel.add(wrapper);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    // OBSERVER CALLBACK METHOD
    @Override
    public void notify(List<DeckDTO> updatedDecks) {
        currentDecks = updatedDecks;
        SwingUtilities.invokeLater(this::refreshData);
    }
    public void updateQuery(String searchQuery){
        this.searchQuery = searchQuery;
        refreshData();


    }
}

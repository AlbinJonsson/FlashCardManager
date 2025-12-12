package org.flashcard.testview;

import org.flashcard.application.dto.DeckDTO;
import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.UserController;
import org.flashcard.controllers.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HomeView extends JPanel implements Observer<List<DeckDTO>> {

    private final DeckController deckController;
    private final UserController userController;
    private final AppFrame appFrame;

    private JPanel gridPanel;

    public HomeView(DeckController deckController, UserController userController, AppFrame appFrame) {
        this.deckController = deckController;
        this.userController = userController;
        this.appFrame = appFrame;

        deckController.getDecksObservable().addListener(this);

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("To be rehearsed Today");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));
        add(title, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        gridPanel.setBackground(Color.WHITE);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // ⬇⬇⬇ NYTT: nu skapar vi scrollPane som variabel och tar bort border
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(null); // <-- tar bort tunna svarta linjen
        add(scrollPane, BorderLayout.CENTER);
        // ⬆⬆⬆ END NEW
    }

    public void applyFilter(String text, Integer tagId) {
        refreshData(text, tagId);
    }

    public void refreshData(String text, Integer tagId) {
        gridPanel.removeAll();

        Integer userId = userController.getCurrentUserId();
        List<DeckDTO> decks = deckController.searchDecks(userId, text, tagId)
                .stream()
                .filter(d -> d.getDueCount() > 0)
                .toList();

        if (decks.isEmpty()) {
            JLabel lbl = new JLabel("No cards to study today!");
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            gridPanel.add(lbl);
        } else {
            for (DeckDTO d : decks) {
                gridPanel.add(new DeckCard(d,
                        e -> appFrame.startStudySession(d.getId(), "today")));
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    @Override
    public void notify(List<DeckDTO> data) {
        refreshData(null, null);
    }
}

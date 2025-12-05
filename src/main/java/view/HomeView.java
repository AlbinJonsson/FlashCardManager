package view;

import org.flashcard.application.dto.DeckDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HomeView extends JPanel {

    protected JPanel headerPanel;
    protected JLabel titleLabel;
    protected JPanel decksPanel;
    protected JScrollPane scrollPane;

    public HomeView() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG);

        initComponents();
        layoutComponents();
    }

    private void initComponents() {

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(true);
        headerPanel.setBackground(Theme.BG);

        // Headern har fast höjd
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setMinimumSize(new Dimension(0, 60));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Titel (kan ändras i subklasser)
        titleLabel = new JLabel("To be rehearsed today");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Theme.TEXT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        headerPanel.add(titleLabel, BorderLayout.WEST);

        decksPanel = new JPanel();
        decksPanel.setLayout(new GridLayout(0, 3, 20, 20));
        decksPanel.setOpaque(true);
        decksPanel.setBackground(Theme.BG);

        // === WRAPPER PANEL WITH EDGE PADDING ===
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // top, left, bottom, right
        wrapper.add(decksPanel, BorderLayout.CENTER);


        scrollPane = new JScrollPane(wrapper);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBackground(Theme.BG);
    }

    private void layoutComponents() {
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }


    public void setDecks(List<DeckDTO> decks) {
        decksPanel.removeAll();

        for (DeckDTO deck : decks) {
            DeckCard card = new DeckCard();
            card.setCardData(
                    deck.getCardCount(),
                    1,
                    deck.getTitle(),
                    deck.getSafeTagTitle(),
                    deck.getSafeTagColor()
            );

            decksPanel.add(card);
        }

        decksPanel.revalidate();
        decksPanel.repaint();
    }
}

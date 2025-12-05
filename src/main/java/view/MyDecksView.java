package view;

import org.flashcard.application.dto.DeckDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyDecksView extends HomeView {

    private JButton addDeckbutton;

    public MyDecksView() {
        super();
        titleLabel.setText("My Decks");
        initAddDeckButton();
    }

    private void initAddDeckButton() {
        addDeckbutton = new JButton("Add Deck");
        addDeckbutton.setFocusPainted(false);
        addDeckbutton.setOpaque(true);

        addDeckbutton.setBackground(new Color(245, 245, 245));
        addDeckbutton.setForeground(Color.BLACK);
        addDeckbutton.setFont(Theme.NORMAL);

        addDeckbutton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200), 1, true),
                BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));

        addDeckbutton.setPreferredSize(new Dimension(130, 36));

        addDeckbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                addDeckbutton.setBackground(new Color(235, 235, 235));
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                addDeckbutton.setBackground(new Color(245, 245, 245));
            }
        });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rightPanel.setOpaque(false);
        rightPanel.add(addDeckbutton);

        headerPanel.add(rightPanel, BorderLayout.EAST);
    }

    public void updateDecks(List<DeckDTO> decks) {
        setDecks(decks);
        revalidate();
        repaint();
    }
}

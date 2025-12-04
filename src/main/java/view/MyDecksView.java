package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyDecksView extends HomeView {

    private JButton editButton;

    public MyDecksView() {
        super();
        titleLabel.setText("My Decks");
        initEditButton();
    }

    private void initEditButton() {
        editButton = new JButton("Edit Decks");
        editButton.setFocusPainted(false);
        editButton.setOpaque(true);

        editButton.setBackground(new Color(245, 245, 245));
        editButton.setForeground(Color.BLACK);
        editButton.setFont(Theme.NORMAL);

        editButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200), 1, true),
                BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));

        editButton.setPreferredSize(new Dimension(130, 36));

        editButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) {
                editButton.setBackground(new Color(235, 235, 235));
            }
            @Override public void mouseExited(java.awt.event.MouseEvent e) {
                editButton.setBackground(new Color(245, 245, 245));
            }
        });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rightPanel.setOpaque(false);
        rightPanel.add(editButton);

        headerPanel.add(rightPanel, BorderLayout.EAST);
    }

    public void updateDecks(List<String> deckNames) {
        setDecks(deckNames);
    }
}

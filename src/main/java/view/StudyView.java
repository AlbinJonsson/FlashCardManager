package view;

import javax.swing.*;
import java.awt.*;

public class StudyView extends JPanel {

    private JLabel cardFront;
    private JButton showBackButton;
    private JButton againBtn;
    private JButton hardBtn;
    private JButton mediumBtn;
    private JButton easyBtn;

    public StudyView() {
        setBackground(Theme.BG);
        setLayout(new BorderLayout());

        cardFront = new JLabel("Study session not started");
        cardFront.setForeground(Theme.TEXT);
        cardFront.setFont(new Font("SansSerif", Font.BOLD, 26));
        cardFront.setHorizontalAlignment(SwingConstants.CENTER);

        add(cardFront, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setOpaque(false);

        againBtn = new JButton("Again");
        hardBtn = new JButton("Hard");
        mediumBtn = new JButton("Medium");
        easyBtn = new JButton("Easy");

        btnPanel.add(againBtn);
        btnPanel.add(hardBtn);
        btnPanel.add(mediumBtn);
        btnPanel.add(easyBtn);

        add(btnPanel, BorderLayout.SOUTH);
    }

    public void showCardFront(String text) {
        cardFront.setText(text);
    }

    public JButton getAgainButton() { return againBtn; }
    public JButton getHardButton() { return hardBtn; }
    public JButton getMediumButton() { return mediumBtn; }
    public JButton getEasyButton() { return easyBtn; }
}

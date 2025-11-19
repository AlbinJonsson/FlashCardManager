package View;

import View.Theme;

import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {

    public HomePage() {
        setLayout(new BorderLayout());
        setBackground(Theme.BG);

        //         VÄNSTER SIDOPANEL (FRIENDS)
        JPanel friendsPanel = new JPanel();
        friendsPanel.setPreferredSize(new Dimension(200, 0));
        friendsPanel.setBackground(new Color(245, 230, 200));
        friendsPanel.setLayout(new BoxLayout(friendsPanel, BoxLayout.Y_AXIS));
        friendsPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JLabel title = new JLabel("Friends");
        title.setFont(Theme.TITLE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        friendsPanel.add(title);

        friendsPanel.add(Box.createVerticalStrut(15));


        // Exempel-vänner (hårdkodade för UI)
        String[] names = {"Hasse", "Pelle", "Peter", "Hasse", "Pelle", "Peter"};
        for (String name : names) {
            JLabel friend = new JLabel("• " + name);
            friend.setFont(Theme.NORMAL);
            friend.setAlignmentX(Component.LEFT_ALIGNMENT);
            friend.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            friendsPanel.add(friend);
        }

        // Lägg vänsterpanel i BorderLayout.WEST
        add(friendsPanel, BorderLayout.WEST);

        //                 CENTER
        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);

        // Titel över korten
        JLabel centerTitle = new JLabel("To rehearse today");
        centerTitle.setFont(Theme.TITLE);
        centerTitle.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        center.add(centerTitle, BorderLayout.NORTH);


        //         GRID MED FLASHCARDS
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(2, 3, 50, 50));
        grid.setOpaque(false);
        grid.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));


        for (int i = 0; i < 6; i++) {
            grid.add(createMockCard("Biology"));
        }



        center.add(grid, BorderLayout.CENTER);
        // Lägg center-panelen i mitten av sidan
        add(center, BorderLayout.CENTER);



    }

    private JPanel createMockCard(String title) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Theme.CARD_BG);
        card.setPreferredSize(new Dimension(150, 110));

        // Rundade hörn + soft skugga
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 170, 120), 2, true),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)
                )
        );

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setForeground(Color.DARK_GRAY);

        card.add(label, BorderLayout.CENTER);

        return card;
    }
}

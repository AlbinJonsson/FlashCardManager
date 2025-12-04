package view;

import org.flashcard.models.dataclasses.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FriendsView extends JPanel {

    private JButton toggleButton;
    private JPanel headerPanel;
    private JPanel listPanel;
    private JScrollPane scrollPane;
    private boolean isOpen = true;
    private JLabel friendsLabel;

    private List<User> userList = List.of();
    private User currentUser;

    public FriendsView() {
        initComponents();
        layoutComponents();
        styleComponents();
        addListeners();
    }

    private void initComponents() {
        toggleButton = new JButton("⮜");
        toggleButton.setFocusPainted(false);
        toggleButton.setBorder(null);

        friendsLabel = new JLabel("Friends");
        friendsLabel.setForeground(Theme.TEXT);
        friendsLabel.setFont(Theme.MEDIUM);
        friendsLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 60));

        headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        headerPanel.setOpaque(false);
        headerPanel.add(friendsLabel);
        headerPanel.add(toggleButton);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Theme.BORDER));
        setPreferredSize(new Dimension(175, getPreferredSize().height));
    }

    private void addListeners() {
        toggleButton.addActionListener(e -> {
            if (isOpen) close(); else open();
        });
    }

    public void onUserChanged(User user) {
        this.currentUser = user;
        refreshFriendsList();  // uppdatera UI när user ändras
    }

    public void setUsers(List<User> users) {
        this.userList = users;
        refreshFriendsList();
    }

    private void refreshFriendsList() {
        listPanel.removeAll();

        for (User u : userList) {
            JButton b = new JButton(u.getUsername());
            b.setFocusPainted(false);
            b.setContentAreaFilled(false);
            b.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            b.setForeground(Theme.TEXT);
            b.setFont(Theme.NORMAL);
            b.setHorizontalAlignment(SwingConstants.LEFT);

            if (currentUser != null && currentUser.getId().equals(u.getId())) {
                b.setFont(Theme.NORMAL.deriveFont(Font.BOLD));
            }

            listPanel.add(b);
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    public void open() {
        isOpen = true;
        toggleButton.setText("⮜");
        listPanel.setVisible(true);
        friendsLabel.setVisible(true);
        setPreferredSize(new Dimension(175, getHeight()));
    }

    public void close() {
        isOpen = false;
        toggleButton.setText("⮞");
        listPanel.setVisible(false);
        friendsLabel.setVisible(false);
        setPreferredSize(new Dimension(25, getHeight()));
    }

    public boolean isOpen() { return isOpen; }

    private void styleComponents() {
        setBackground(Theme.BG);
        toggleButton.setBackground(Theme.BG);
        toggleButton.setForeground(Theme.TEXT);
    }
}

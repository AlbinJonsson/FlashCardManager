package view;

import org.flashcard.application.dto.UserDTO;
import org.flashcard.controllers.UserController;
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

    public void refreshFriendsList() {
        listPanel.removeAll(); // clear existing entries

        // Fetch all users from controller
        List<UserDTO> users = userController.getAllUsers();
        UserDTO currentUser = userController.getCurrentUser(); // get the currently signed-in user


        for (UserDTO user : users) {
            JButton userButton = new JButton(user.getUsername());
            userButton.setFocusPainted(false);
            userButton.setContentAreaFilled(false);
            userButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            userButton.setForeground(Theme.TEXT);
            userButton.setFont(Theme.NORMAL);
            userButton.setHorizontalAlignment(SwingConstants.LEFT);


            // Make current user bold
            if (currentUser != null && currentUser.getUsername().equals(user.getUsername())) {
                userButton.setFont(Theme.NORMAL.deriveFont(Font.BOLD));
            } else {
                userButton.setFont(Theme.NORMAL);
            }



            // When clicked, "sign in" as this user
            userButton.addActionListener(e -> {
                userController.loginByUserId(user.getId());
                JOptionPane.showMessageDialog(this,
                        "You are now signed in as: " + user.getUsername(),
                        "Signed In",
                        JOptionPane.INFORMATION_MESSAGE);

            });

            listPanel.add(userButton);
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private void openUserProfile(User user) {
        // Auto-login as this user
        userController.loginByUserId(user.getId());

        // Optionally, update UI or navigate to Home
        JOptionPane.showMessageDialog(this,
                "You are now signed in as: " + user.getUsername(),
                "Signed In",
                JOptionPane.INFORMATION_MESSAGE);

        // For example, hide friends panel or refresh views
        close();
    }







}

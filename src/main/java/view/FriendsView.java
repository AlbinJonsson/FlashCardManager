package view;

import org.flashcard.controllers.UserController;
import org.flashcard.application.dto.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FriendsView extends JPanel {

    private final UserController userController;

    private JButton toggleButton;
    private JPanel headerPanel;
    private JPanel listPanel;
    private JScrollPane scrollPane;
    private boolean isOpen = true;
    private JLabel friendsLabel;

    public FriendsView(UserController userController) {
        this.userController = userController;

        initComponents();
        layoutComponents();
        styleComponents();
        addListeners();

        refreshFriendsList();   // ← viktig!
    }

    private void initComponents(){

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

    public void open() {
        isOpen = true;
        toggleButton.setText("⮜");

        listPanel.setVisible(true);
        friendsLabel.setVisible(true);

        setPreferredSize(new Dimension(175, getHeight()));

        revalidate();
        repaint();
    }

    public void close() {
        isOpen = false;
        toggleButton.setText("⮞");

        listPanel.setVisible(false);
        friendsLabel.setVisible(false);

        setPreferredSize(new Dimension(25, getHeight()));

        revalidate();
        repaint();
    }

    public boolean isOpen() { return isOpen; }

    private void styleComponents() {
        setBackground(Theme.BG);
        toggleButton.setBackground(Theme.BG);
        toggleButton.setForeground(Theme.TEXT);
    }

    /**
     * Kallas när vy ska visa alla användare.
     * UserController = din riktiga controller → hämtar UserDTO-lista.
     */
    public void refreshFriendsList() {

        listPanel.removeAll();

        List<UserDTO> users = userController.getAllUsers();
        Integer currentUserId = userController.getCurrentUserId();

        for (UserDTO user : users) {

            JButton userButton = new JButton(user.getUsername());
            userButton.setFocusPainted(false);
            userButton.setContentAreaFilled(false);
            userButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            userButton.setForeground(Theme.TEXT);
            userButton.setFont(Theme.NORMAL);
            userButton.setHorizontalAlignment(SwingConstants.LEFT);

            if (currentUserId != null && currentUserId.equals(user.getId())) {
                userButton.setFont(Theme.NORMAL.deriveFont(Font.BOLD));
            }

            userButton.addActionListener(e -> {
                userController.loginByUserId(user.getId());
                refreshFriendsList(); // uppdatera bold-markering
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
}

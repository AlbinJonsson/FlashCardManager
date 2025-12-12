package org.flashcard.testview;

import org.flashcard.application.dto.UserDTO;
import org.flashcard.controllers.UserController;
import org.flashcard.controllers.DeckController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.function.Consumer;

public class Navbar extends JPanel {

    private final Consumer<String> navigationCallback;
    private JTextField searchBar;
    private DeckController deckController;
    private UserController userController;
    private JButton userButton;
    private final String hint = "Search...";
    private final Font lostFont = new Font("Tahoma", Font.ITALIC, 11);

    public Navbar(DeckController deckController, UserController userController, Consumer<String> navigationCallback) {
        this.deckController = deckController;
        this.userController = userController;
        this.navigationCallback = navigationCallback;

        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(50, 50, 50));


        add(createUserDropdownButton());
        add(createNavButton("Home", "Home"));
        add(createNavButton("My Decks", "MyDecks"));


        // Subscribe to user changes
        userController.getCurrentUserObservable().addListener(userDTO -> {
            if (userDTO != null) {
                userButton.setText(userDTO.getUsername() + " ⬇");
                deckController.getDecksObservable().notifyListeners(
                        deckController.getAllDecksForUser(userDTO.getId())
                );
            }
        });
    }

    private JButton createUserDropdownButton() {
        userButton = new JButton("Profile: " +
                (userController.getCurrentUser() != null ?
                        userController.getCurrentUser().getUsername() : "Guest"));

        userButton.setHorizontalAlignment(SwingConstants.LEFT); // text and icon align left
        userButton.setIconTextGap(5); // small gap between icon and text

        userButton.setFocusPainted(false);
        userButton.setBackground(new Color(70, 70, 70));
        userButton.setForeground(Color.WHITE);
        userButton.setBorderPainted(false);

        userButton.addActionListener(e -> showUserPopupMenu(userButton));

        return userButton;
    }

    private void showUserPopupMenu(JButton parentButton) {
        JPopupMenu menu = new JPopupMenu();

        List<UserDTO> users = userController.getAllUsers();

        for (UserDTO user : users) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setOpaque(true);

            JLabel nameLabel = new JLabel(user.getUsername());
            nameLabel.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
            nameLabel.setForeground(Color.BLACK);
            nameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    userController.loginByUserId(user.getId());
                    parentButton.setText("User: " + user.getUsername());
                    menu.setVisible(false);
                }
            });

            JButton deleteButton = new JButton("🗑");
            deleteButton.setMargin(new Insets(2, 4, 2, 4));
            deleteButton.setFocusPainted(false);
            deleteButton.setBackground(new Color(220, 50, 50));
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setBorderPainted(false);
            deleteButton.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                        Navbar.this,
                        "Are you sure you want to delete user '" + user.getUsername() + "'?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    userController.deleteUser(user.getId());
                    menu.setVisible(false);
                    SwingUtilities.invokeLater(() -> showUserPopupMenu(parentButton));
                }
            });

            panel.add(nameLabel, BorderLayout.CENTER);
            panel.add(deleteButton, BorderLayout.EAST);

            JMenuItem item = new JMenuItem();
            item.setLayout(new BorderLayout());
            item.add(panel, BorderLayout.CENTER);
            item.setEnabled(false); // prevent default highlight
            menu.add(item);
        }

        menu.addSeparator();

        JMenuItem createUser = new JMenuItem("➕ Create New User…");
        createUser.addActionListener(e -> {
            String newUser = JOptionPane.showInputDialog(
                    this,
                    "Enter a new username:",
                    "Create New User",
                    JOptionPane.PLAIN_MESSAGE
            );

            if (newUser != null && !newUser.trim().isEmpty()) {
                UserDTO created = userController.createUser(newUser.trim());
                userController.loginByUserId(created.getId());
                parentButton.setText("User: " + created.getUsername());
            }
        });
        menu.add(createUser);

        menu.show(parentButton, 0, parentButton.getHeight());
    }

    private JButton createNavButton(String label, String viewName) {
        JButton btn = new JButton(label);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(70, 70, 70));
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.addActionListener(e -> navigationCallback.accept(viewName));
        return btn;
    }


}

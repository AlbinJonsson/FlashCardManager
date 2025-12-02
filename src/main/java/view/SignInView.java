package view;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

public class SignInView extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JLabel messageLabel;

    // Listener: (username, password) -> controller handles login
    private BiConsumer<String, String> onSignIn;

    public SignInView() {
        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initComponents() {
        setBackground(Theme.BG);
        setLayout(new GridBagLayout());

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        signInButton = new JButton("Sign In");
        signInButton.setFocusPainted(false);

        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        add(signInButton, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        add(messageLabel, gbc);
    }

    private void addListeners() {
        signInButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (onSignIn != null) {
                onSignIn.accept(username, password);
            }
        });
    }

    // Let MainFrame assign the login handler
    public void setOnSignIn(BiConsumer<String, String> listener) {
        this.onSignIn = listener;
    }

    // Show message from controller
    public void showMessage(String text) {
        messageLabel.setText(text);
    }

    // Clear fields
    public void clear() {
        usernameField.setText("");
        passwordField.setText("");
        messageLabel.setText("");
    }
}

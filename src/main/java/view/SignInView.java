package view;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

public class SignInView extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton;
    private JLabel messageLabel;

    private BiConsumer<String, String> onSignIn;

    public SignInView() {
        initComponents();
        layoutComponents();
        addListeners();
    }

    private void initComponents() {
        setBackground(Theme.BG);
        setLayout(new GridBagLayout());

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(260, 32));

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(260, 32));

        signInButton = new JButton("Sign In");
        signInButton.setPreferredSize(new Dimension(260, 36));
        signInButton.setFocusPainted(false);

        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
    }

    private void layoutComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);

        GridBagConstraints f = new GridBagConstraints();
        f.insets = new Insets(10, 10, 10, 10);
        f.fill = GridBagConstraints.NONE;
        f.anchor = GridBagConstraints.WEST;
        f.gridx = 0;

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Theme.TEXT);
        formPanel.add(userLabel, f);

        f.gridy = 1;
        formPanel.add(usernameField, f);

        f.gridy = 2;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Theme.TEXT);
        formPanel.add(passLabel, f);

        f.gridy = 3;
        formPanel.add(passwordField, f);

        f.gridy = 4;
        formPanel.add(signInButton, f);

        f.gridy = 5;
        formPanel.add(messageLabel, f);

        GridBagConstraints main = new GridBagConstraints();
        main.gridx = 0;
        main.gridy = 0;
        main.weightx = 1;
        main.weighty = 1;
        main.anchor = GridBagConstraints.CENTER;

        add(formPanel, main);
    }

    private void addListeners() {
        signInButton.addActionListener(e -> {
            if (onSignIn != null) {
                onSignIn.accept(
                        usernameField.getText().trim(),
                        new String(passwordField.getPassword()).trim()
                );
            }
        });
    }

    public void setOnSignIn(BiConsumer<String, String> listener) {
        this.onSignIn = listener;
    }

    public void showMessage(String text) {
        messageLabel.setText(text);
    }

    public void clear() {
        usernameField.setText("");
        passwordField.setText("");
        messageLabel.setText("");
    }
}

package view;

import javax.swing.*;
import java.awt.*;

public class CreateDeckPopup extends JDialog {

    private JTextField titleField;
    private JTextField tagField;

    private JButton createButton;
    private JButton closeButton;

    private Runnable onClose;
    private CreateDeckCallback onCreate;

    public interface CreateDeckCallback {
        void create(String title, String tag);
    }

    public CreateDeckPopup(JFrame parent) {
        super(parent, true);
        setUndecorated(true);

        // Light overlay
        getContentPane().setBackground(new Color(0, 0, 0, 70));
        setLayout(new GridBagLayout());

        // --- Popup Card ---
        JPanel card = new JPanel();
        card.setBackground(Theme.BG);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));
        card.setPreferredSize(new Dimension(480, 420));
        card.setMaximumSize(new Dimension(480, 420));

        // --- Header Row (Centered title + X on right) ---
        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);

        JLabel header = new JLabel("Create Deck", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 26));
        header.setForeground(Theme.TEXT);
        header.setHorizontalAlignment(SwingConstants.CENTER);

        closeButton = new JButton("✕");
        closeButton.setFocusPainted(false);
        closeButton.setBorder(null);
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        closeButton.setForeground(Theme.TEXT);
        closeButton.setContentAreaFilled(false);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        headerRow.add(header, BorderLayout.CENTER);
        headerRow.add(closeButton, BorderLayout.EAST);

        // --- Labels (bigger & spaced) ---
        JLabel titleLabel = new JLabel("Title");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(Theme.TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleField = new JTextField();
        titleField.setPreferredSize(new Dimension(380, 40));
        titleField.setMaximumSize(new Dimension(380, 40));

        JLabel tagLabel = new JLabel("Tag");
        tagLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        tagLabel.setForeground(Theme.TEXT);
        tagLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        tagField = new JTextField();
        tagField.setPreferredSize(new Dimension(380, 40));
        tagField.setMaximumSize(new Dimension(380, 40));

        // --- Create Button ---
        createButton = new JButton("Create Deck");
        styleMainButton(createButton);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRow.setOpaque(false);
        btnRow.add(createButton);

        // --- Add components ---
        card.add(headerRow);
        card.add(Box.createVerticalStrut(30));

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(titleField);
        card.add(Box.createVerticalStrut(20));

        card.add(tagLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(tagField);

        card.add(Box.createVerticalStrut(35));
        card.add(btnRow);

        add(card);

        // --- Listeners ---
        closeButton.addActionListener(e -> {
            if (onClose != null) onClose.run();
            close();
        });

        createButton.addActionListener(e -> {
            if (onCreate != null) {
                onCreate.create(titleField.getText(), tagField.getText());
            }
        });

        // Fullscreen overlay
        setSize(parent.getWidth(), parent.getHeight());
        setLocationRelativeTo(parent);
    }

    private void styleMainButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Theme.Butten_BG);
        btn.setPreferredSize(new Dimension(200, 45));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
    }

    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    public void setOnCreate(CreateDeckCallback onCreate) {
        this.onCreate = onCreate;
    }

    public void open() {
        setVisible(true);
    }

    public void close() {
        setVisible(false);
        dispose();
    }
}

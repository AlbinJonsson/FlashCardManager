package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SearchBar extends JPanel {

    private final JTextField field;
    private final String placeholder;

    public SearchBar(String placeholder, int width) {
        this.placeholder = placeholder;

        setLayout(new BorderLayout());
        setOpaque(false);                        // panelen ritas själv
       //etPreferredSize(new Dimension(width, 36));
        setPreferredSize(new Dimension(width, 42));
        JLabel icon = new JLabel("     🔍  ");
        icon.setForeground(Color.DARK_GRAY);

        // --- TEXTFÄLT ---
        field = new JTextField();
        field.setOpaque(false);                  // ingen egen bakgrund
        field.setBorder(null);                   // ingen border
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setForeground(Color.GRAY);
        field.setText(placeholder);

        // Placeholderlogik
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });

        // Layout
        add(icon, BorderLayout.WEST);
        add(field, BorderLayout.CENTER);
        //setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
    }

    // --- RITAR SJÄLVA RUTAN ---
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = getHeight() - 8;          // gör rundning dynamisk
        int padding = 4;                    // avstånd från panelkant

        int w = getWidth() - padding * 2;
        int h = getHeight() - padding * 2;

        // bakgrund
        g2.setColor(new Color(235, 225, 235));
        g2.fillRoundRect(padding, padding, w, h, arc, arc);

        // kant
        g2.setColor(new Color(150, 150, 150));
        g2.drawRoundRect(padding, padding, w, h, arc, arc);
    }



    public String getText() {
        return field.getText();
    }

    public JTextField getField() {
        return field;
    }
}

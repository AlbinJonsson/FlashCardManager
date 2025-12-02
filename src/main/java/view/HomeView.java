package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HomeView extends JPanel {

    protected JPanel headerPanel;
    protected JLabel titleLabel;
    protected JPanel decksPanel;
    protected JScrollPane scrollPane;

    public HomeView() {
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY); // debug background

        initComponents();
        layoutComponents();
        styleComponents();
    }

    private void initComponents() {
        // Header
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        titleLabel = new JLabel("Home");
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Decks container
        decksPanel = new JPanel();
        decksPanel.setLayout(new BoxLayout(decksPanel, BoxLayout.Y_AXIS));
        decksPanel.setOpaque(true);
        decksPanel.setBackground(Color.CYAN); // debug background

        scrollPane = new JScrollPane(decksPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(true);
        scrollPane.getViewport().setBackground(Color.CYAN); // debug
    }

    private void layoutComponents() {
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void styleComponents() {
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    }

    // Set decks as list of strings
    public void setDecks(List<String> deckNames) {
        decksPanel.removeAll();
        for (String name : deckNames) {
            JLabel label = new JLabel(name);
            label.setFont(new Font("Arial", Font.PLAIN, 18));
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setOpaque(false);
            wrapper.add(label, BorderLayout.CENTER);

            decksPanel.add(wrapper);
        }

        decksPanel.revalidate();
        decksPanel.repaint();
    }
}

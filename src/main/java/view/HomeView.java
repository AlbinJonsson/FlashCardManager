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
        setBackground(Theme.BG);

        initComponents();
        layoutComponents();
        styleComponents();
    }

    private void initComponents() {

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(true);
        headerPanel.setBackground(Theme.BG);
        headerPanel.setPreferredSize(new Dimension(0, 60));

        titleLabel = new JLabel("Home");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        headerPanel.add(titleLabel, BorderLayout.WEST);

        decksPanel = new JPanel();
        decksPanel.setLayout(new BoxLayout(decksPanel, BoxLayout.Y_AXIS));
        decksPanel.setOpaque(true);
        decksPanel.setBackground(Theme.BG);

        scrollPane = new JScrollPane(decksPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getViewport().setBackground(Theme.BG);
    }

    private void layoutComponents() {
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void styleComponents() {
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    }

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

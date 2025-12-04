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
    }

    private void initComponents() {

        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(true);
        headerPanel.setBackground(Theme.BG);

        // Headern har fast höjd
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setMinimumSize(new Dimension(0, 60));
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Titel (kan ändras i subklasser)
        titleLabel = new JLabel("To be rehearsed today");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Theme.TEXT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Panel som listar decks
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

    /**
     * View är dum → den tar bara emot en lista från controller via MainFrame.
     */
    public void setDecks(List<String> deckNames) {
        decksPanel.removeAll();

        for (String name : deckNames) {
            JLabel label = new JLabel(name);

            label.setFont(new Font("SansSerif", Font.PLAIN, 18));
            label.setForeground(Theme.TEXT);
            label.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 10));

            decksPanel.add(label);
        }

        decksPanel.revalidate();
        decksPanel.repaint();
    }
}

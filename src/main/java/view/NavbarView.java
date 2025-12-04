package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.function.Consumer;

public class NavbarView extends JPanel {

    private NavigationListener listener;

    private JButton homeBtn;
    private JButton decksBtn;
    private JButton scheduleBtn;
    private JButton signinBtn;

    private SearchBar searchBox;

    private JPanel leftPanel;
    private JPanel centerPanel;
    private JPanel rightPanel;

    private static final Font ACTIVE = Theme.NORMAL.deriveFont(Font.BOLD);

    public NavbarView() {
        setLayout(new BorderLayout());
        setBackground(Theme.NAV_BG);
        setPreferredSize(new Dimension(0, 70));

        initComponents();
        layoutComponents();
        styleComponents();
        addListeners();
    }

    // --- IMPORTANT PART ---
    public void setOnNavigate(NavigationListener listener) {
        this.listener = listener;
    }
    // ------------------------

    private void initComponents() {
        homeBtn = new JButton("Home");
        decksBtn = new JButton("My Decks");
        scheduleBtn = new JButton("Schedule");
        signinBtn = new JButton("Sign In");

        searchBox = new SearchBar("Search ", 280);

        leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        leftPanel.setOpaque(false);
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        centerPanel.setOpaque(false);
        rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        rightPanel.setOpaque(false);
    }

    private void layoutComponents() {
        leftPanel.add(homeBtn);
        leftPanel.add(decksBtn);
        leftPanel.add(scheduleBtn);

        centerPanel.add(searchBox);

        rightPanel.add(signinBtn);

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

    private void styleButton(JButton btn) {
        btn.setBackground(Theme.NAV_BG);
        btn.setForeground(Theme.TEXT);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFont(Theme.NORMAL);
        btn.setPreferredSize(new Dimension(120, 32));
    }

    private void styleComponents() {
        styleButton(homeBtn);
        styleButton(decksBtn);
        styleButton(scheduleBtn);
        styleButton(signinBtn);

        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Theme.BORDER));
    }

    private void addListeners() {
        homeBtn.addActionListener(e -> listener.onNavigate("Home"));
        decksBtn.addActionListener(e -> listener.onNavigate("MyDecks"));
        scheduleBtn.addActionListener(e -> listener.onNavigate("Schedule"));
        signinBtn.addActionListener(e -> listener.onNavigate("SignIn"));
    }

    public void setOnSearch(Consumer<String> callback) {
        searchBox.getField().getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { callback.accept(searchBox.getText()); }
            @Override public void removeUpdate(DocumentEvent e) { callback.accept(searchBox.getText()); }
            @Override public void changedUpdate(DocumentEvent e) { callback.accept(searchBox.getText()); }
        });
    }

    public void highlight(String pageName) {
        homeBtn.setFont(Theme.NORMAL);
        decksBtn.setFont(Theme.NORMAL);
        scheduleBtn.setFont(Theme.NORMAL);
        signinBtn.setFont(Theme.NORMAL);

        switch (pageName) {
            case "Home" -> homeBtn.setFont(ACTIVE);
            case "MyDecks" -> decksBtn.setFont(ACTIVE);
            case "Schedule" -> scheduleBtn.setFont(ACTIVE);
            case "SignIn" -> signinBtn.setFont(ACTIVE);
        }
    }
}

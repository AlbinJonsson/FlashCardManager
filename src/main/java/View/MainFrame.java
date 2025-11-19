package View;

import View.components.SearchBar;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    // CardLayout låter oss växla mellan olika sidor (HOME, DECKS, osv)
    private final CardLayout layout = new CardLayout();

    // Container som håller sidorna
    private JPanel container = new JPanel(layout);

    public MainFrame() {
        super("Flashcards");

        // Storlek på fönstret
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centrerar fönstret

        //              NAV BAR
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(Theme.NAV_BG);
        nav.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // ---- Vänster del (Home, My Decks, osv) ----
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 10));
        left.setOpaque(false); // så bakgrundsfärgen från nav syns igenom
        left.add(new JLabel("Home"));
        left.add(new JLabel("My Decks"));
        left.add(new JLabel("My Schedule"));
        nav.add(left, BorderLayout.WEST);

        // ---- Sökfältet i mitten ----
        SearchBar search = new SearchBar("Search…", 500);

        JPanel searchWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
        searchWrapper.setOpaque(false);
        searchWrapper.add(search);

        nav.add(searchWrapper, BorderLayout.CENTER);


        // ---- Höger del (Sign In) ----
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        right.setOpaque(false);
        right.add(new JLabel("Sign In"));
        nav.add(right, BorderLayout.EAST);

        // Lägg nav bar högst upp
        add(nav, BorderLayout.NORTH);

        //           SIDOR / CENTRAL YTA
        container.add(new HomePage(), "HOME");

        // Lägg till container i mitten
        add(container, BorderLayout.CENTER);
    }
    //  Visar en viss sida i CardLayout
    public void showPage(String key) {
        layout.show(container, key);
    }
}

package view;

import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.StudyController;
import org.flashcard.controllers.UserController;
import org.flashcard.models.dataclasses.Deck;
import org.flashcard.models.dataclasses.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TestFrame extends JFrame {

    private JPanel contentPanel;
    private JLabel userLabel;

    private final UserController userController;
    private final DeckController deckController;
    private final StudyController studyController;

    public TestFrame(UserController userController,
                     StudyController studyController,
                     DeckController deckController) {

        this.userController = userController;
        this.studyController = studyController;
        this.deckController = deckController;

        setTitle("Test Frame - Blank Canvas");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        layoutComponents();
        refreshCurrentUser();
        refreshDecks();
    }

    private void initComponents() {
        // Label to display current user
        userLabel = new JLabel();
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userLabel.setForeground(Color.BLACK);

        // Main content panel
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.DARK_GRAY);
        contentPanel.setLayout(new GridLayout(0, 3, 20, 20)); // grid: 3 columns, rows auto
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Add user label at the top
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.LIGHT_GRAY);
        headerPanel.add(userLabel);

        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(contentPanel), BorderLayout.CENTER); // scroll if many decks
    }

    // Call whenever current user changes
    public void refreshCurrentUser() {
        User currentUser = userController.getUserById(1);
        userController.setCurrentUser(currentUser);
        if (currentUser != null) {
            userLabel.setText("Current User: " + currentUser.getUsername());
        } else {
            userLabel.setText("No user logged in");
        }
    }

    // Display all decks for current user in contentPanel
    public void refreshDecks() {
        contentPanel.removeAll();

        User currentUser = userController.getCurrentUser();
        if (currentUser != null) {
            List<Deck> decks = deckController.getDecksForUser(currentUser.getId());

            for (Deck deck : decks) {
                JPanel card = createDeckCard(deck);
                contentPanel.add(card);
            }
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Simple deck card panel
    private JPanel createDeckCard(Deck deck) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        card.setPreferredSize(new Dimension(200, 140)); // space for tag

        // Deck title at center
        JLabel nameLabel = new JLabel(deck.getTitle());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(nameLabel, BorderLayout.CENTER);

        // Tag at bottom (if it exists)
        if (deck.getTag() != null) {
            var tag = deck.getTag();

            JLabel tagLabel = new JLabel(tag.getTitle());
            tagLabel.setOpaque(true);


            String hex = tag.getColor();
            if (!hex.startsWith("#")) hex = "#" + hex;
            tagLabel.setBackground(Color.decode(hex));


            tagLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            tagLabel.setFont(new Font("Arial", Font.PLAIN, 12));

            JPanel tagPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
            tagPanel.setBackground(Color.WHITE);
            tagPanel.add(tagLabel);

            card.add(tagPanel, BorderLayout.SOUTH);
        }

        return card;
    }


}

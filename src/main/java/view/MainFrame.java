package view;

import org.flashcard.controllers.UserController;
import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.StudyController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private final UserController userController;
    private final DeckController deckController;
    private final StudyController studyController;

    private CardLayout cardLayout;
    private JPanel contentPanel;

    private NavbarView navbarView;
    private FriendsView friendsView;

    private HomeView homeView;
    private MyDecksView myDecksView;
    private StudyView studyView;
    private ScheduleView scheduleView;
    private MyAccountView myAccountView;
    private SignInView signInView;

    public MainFrame(UserController userController,
                     StudyController studyController,
                     DeckController deckController) {

        this.userController = userController;
        this.studyController = studyController;
        this.deckController = deckController;

        initComponents();
        layoutComponents();
        setupNavigation();

        showPage("Home");

        setTitle("Flashcard App");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        homeView = new HomeView();
        myDecksView = new MyDecksView();
        studyView = new StudyView();
        scheduleView = new ScheduleView();
        myAccountView = new MyAccountView();
        signInView = new SignInView();

        contentPanel.add(homeView, "Home");
        contentPanel.add(myDecksView, "MyDecks");
        contentPanel.add(studyView, "Study");
        contentPanel.add(scheduleView, "Schedule");
        contentPanel.add(myAccountView, "MyAccount");
        contentPanel.add(signInView, "SignIn");

        navbarView = new NavbarView();
        friendsView = new FriendsView(userController);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        add(navbarView, BorderLayout.NORTH);
        add(friendsView, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void setupNavigation() {

        // ---- SEARCH ----
        navbarView.setOnSearch(text -> {
            Integer uid = userController.getCurrentUserId();
            if (uid != null) {

                var dueDecks = deckController.getDueDecksForUser(uid);

                List<String> results = dueDecks.stream()
                        .map(d -> d.getTitle())
                        .filter(t -> t.toLowerCase().contains(text.toLowerCase()))
                        .toList();

                homeView.setDecks(results);
            }
        });

        // ---- NAV BUTTONS ----
        navbarView.setOnNavigate(this::showPage);


        // ---- SIGN IN ----
        signInView.setOnSignIn((username, password) -> {
            boolean ok = userController.login(username, password);

            if (ok) {
                signInView.clear();
                loadUserData();
                showPage("Home");
            } else {
                signInView.showMessage("Invalid username or password.");
            }
        });


        // ---- CREATE DECK (MY DECKS VIEW) ----
        myDecksView.setOnCreateDeck(() -> {

            CreateDeckPopup popup = new CreateDeckPopup(this);

            // X-stängning i popup
            popup.setOnClose(() -> {
                // Ingen extra logik nu, men vi kan lägga till senare
            });

            popup.setOnCreate((title, tag) -> {
                Integer uid = userController.getCurrentUserId();

                if (uid == null) {
                    JOptionPane.showMessageDialog(this, "You must be logged in to create a deck.");
                    return;
                }

                try {
                    // skapa deck
                    deckController.createDeck(uid, title);

                    // skapa tag (om användaren skriver en)
                    if (tag != null && !tag.trim().isEmpty()) {
                        deckController.createTag(uid, tag.trim(), "AABBCC");
                    }

                    // uppdatera MyDecks
                    loadUserData();

                    popup.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            });

            popup.open();
        });
    }


    /**
     * Laddar ALL DATA från backend för MyDecks och Friends.
     * OBS!!! INTE HomeView → den uppdateras i showPage("Home")
     */
    private void loadUserData() {
        Integer uid = userController.getCurrentUserId();
        if (uid == null) return;

        // ---- ALLA DECKS → MyDecksView ----
        List<String> allDecks = deckController.getAllDecksForUser(uid)
                .stream().map(d -> d.getTitle()).toList();

        myDecksView.updateDecks(allDecks);

        // ---- Friends ----
        friendsView.refreshFriendsList();
    }

    public void showPage(String name) {

        cardLayout.show(contentPanel, name);

        if (name.equals("MyDecks")) {
            loadUserData();
        }

        if (name.equals("Home")) {
            Integer uid = userController.getCurrentUserId();

            if (uid != null) {
                List<String> dueDecks = deckController.getDueDecksForUser(uid)
                        .stream().map(d -> d.getTitle()).toList();

                if (dueDecks.isEmpty()) {
                    homeView.setDecks(List.of());
                } else {
                    homeView.setDecks(dueDecks);
                }
            }
        }

        navbarView.highlight(name);
    }
}

package view;

import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.StudyController;
import org.flashcard.controllers.UserController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

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

    private final UserController userController;
    private final DeckController deckController;
    private final StudyController studyController;

    private JLayeredPane layeredPane;
    private JPanel overlayLayer;

    // NEW: top header bar for each page
    private JPanel topHeaderBar;

    public MainFrame(UserController userController,
                     StudyController studyController,
                     DeckController deckController) {

        this.userController = userController;
        this.studyController = studyController;
        this.deckController = deckController;

        initComponents();
        layoutComponents();
        setNavbarListener();

        showPage("Home");

        setTitle("Flashcard APP");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {

        // ---------- MAIN PAGES ----------
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBounds(0, 60, 1920, 1020);
        contentPanel.setOpaque(true);

        homeView = new HomeView();
        myDecksView = new MyDecksView(userController, deckController);
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

        // ---------- NAVBAR + FRIENDS ----------
        navbarView = new NavbarView();
        friendsView = new FriendsView(userController);

        // ---------- HEADER BAR (PAGE TITLE + BUTTONS) ----------
        topHeaderBar = new JPanel(new BorderLayout());
        topHeaderBar.setPreferredSize(new Dimension(0, 60));
        topHeaderBar.setBackground(Theme.BG);

        // ---------- LAYEREDPANE ----------
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        contentPanel.setBounds(0, 60, 1920, 1020);   // under header bar
        layeredPane.add(contentPanel, JLayeredPane.DEFAULT_LAYER);

        overlayLayer = new JPanel();
        overlayLayer.setOpaque(false);
        overlayLayer.setBounds(0, 0, 1920, 1080);
        layeredPane.add(overlayLayer, JLayeredPane.PALETTE_LAYER);
    }


    private void layoutComponents() {

        setLayout(new BorderLayout());

        // navbar högst upp
        add(navbarView, BorderLayout.NORTH);

        // friends till vänster
        add(friendsView, BorderLayout.WEST);

        // center (ALLA pages ligger i cardLayout inuti contentPanel)
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setOpaque(true);
        centerWrapper.setBackground(Theme.BG);

        centerWrapper.add(contentPanel, BorderLayout.CENTER);

        add(centerWrapper, BorderLayout.CENTER);
    }



    // -------- CHANGE PAGE --------
    public void showPage(String pageName) {
        if (pageName.equals("MyDecks")) {
            myDecksView.refreshDecksForCurrentUser();
        }

        cardLayout.show(contentPanel, pageName);
        navbarView.highlight(pageName);
    }



    private void setNavbarListener() {

        navbarView.setOnNavigate(this::showPage);

        navbarView.setOnSearch(text -> {
            Integer userId = userController.getLoggedInUserId();
            var results = deckController.searchDecks(userId, text);
            homeView.setDecks(results);
        });

        signInView.setOnSignIn((username, password) -> {
            boolean success = userController.login(username, password);
            if (success) {
                signInView.clear();
                showPage("Home");
            } else {
                signInView.showMessage("Invalid username or password.");
            }
        });
    }


    public void showOverlay(Color col) {
        overlayLayer.setOpaque(true);
        overlayLayer.setBackground(col);
        overlayLayer.repaint();
    }

    public void hideOverlay() {
        overlayLayer.setOpaque(false);
        overlayLayer.repaint();
    }
}

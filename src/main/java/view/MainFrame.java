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

    private JLayeredPane layeredPane;   // For overlay if needed
    private JPanel overlayLayer;        // Semi-transparent layer for popups

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

        // LOGIN HANDLER
        signInView.setOnSignIn((username, password) -> {
            boolean success = userController.login(username, password);

            if (success) {
                signInView.showMessage("");   // clear messages
                signInView.clear();           // clear fields
                showPage("Home");             // navigate to home
            } else {
                signInView.showMessage("Invalid username or password.");
            }
        });
    }

    private void initComponents() {

        // --------- CARD LAYOUT PANEL ---------
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Theme.BG);
        contentPanel.setPreferredSize(new Dimension(800, 600));
        contentPanel.setBackground(Color.CYAN); // bright color for debugging


        // --------- ALL VIEWS ---------
        homeView = new HomeView();
        myDecksView = new MyDecksView(userController, deckController);
        studyView = new StudyView();
        scheduleView = new ScheduleView();
        myAccountView = new MyAccountView();
        signInView = new SignInView();

        navbarView = new NavbarView();
        friendsView = new FriendsView(userController);

        // Add pages to CardLayout
        contentPanel.add(homeView, "Home");
        contentPanel.add(myDecksView, "MyDecks");
        contentPanel.add(studyView, "Study");
        contentPanel.add(scheduleView, "Schedule");
        contentPanel.add(myAccountView, "MyAccount");
        contentPanel.add(signInView, "SignIn");

        // --------- OVERLAY LAYER ---------
        overlayLayer = new JPanel();
        overlayLayer.setOpaque(false);
        overlayLayer.setLayout(new BorderLayout());

        // --------- LAYERED PANE ---------
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(new BorderLayout());
        layeredPane.add(contentPanel, BorderLayout.CENTER);
        layeredPane.add(overlayLayer, BorderLayout.CENTER, JLayeredPane.PALETTE_LAYER);

        layeredPane.setBackground(Color.GREEN);      // should show up in center
        contentPanel.setBackground(Color.CYAN);
    }

    private void layoutComponents() {

        setLayout(new BorderLayout());

        // Navbar at top
        add(navbarView, BorderLayout.NORTH);

        // Friends panel at left
        add(friendsView, BorderLayout.WEST);

        // Main content in center
        add(layeredPane, BorderLayout.CENTER);
    }

    // -------- PAGE SWITCHING --------
    public void showPage(String pageName) {
        // Show CardLayout page
        cardLayout.show(contentPanel, pageName);

        // Hide friends panel for SignIn or Schedule
        if ("SignIn".equals(pageName) || "Schedule".equals(pageName)) {
            friendsView.setVisible(false);
        } else {
            friendsView.setVisible(true);
        }

        // Highlight navbar button
        navbarView.highlight(pageName);

        // Refresh UI


        // Refresh MyDecksView if current page
        if ("MyDecks".equals(pageName)) {
            myDecksView.refreshDecksForCurrentUser();
        }

        friendsView.setVisible(!pageName.equals("Schedule") && !pageName.equals("SignIn"));
        navbarView.highlight(pageName);
        revalidate();
        repaint();

        contentPanel.revalidate();
        contentPanel.repaint();
        revalidate();
        repaint();
    }

    private void setNavbarListener() {
        navbarView.setOnNavigate(this::showPage);
    }

    // -------- OVERLAY CONTROL (popup blur etc.) --------
    public void showOverlay(Color transparentDark) {
        overlayLayer.setOpaque(true);
        overlayLayer.setBackground(transparentDark);
        overlayLayer.repaint();
    }

    public void hideOverlay() {
        overlayLayer.setOpaque(false);
        overlayLayer.repaint();
    }
}

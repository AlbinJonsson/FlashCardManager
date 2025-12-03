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

        forceBackgroundRecursively(contentPanel, Theme.BG);


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
        contentPanel = new JPanel(cardLayout) {
            @Override
            public boolean isOpaque() {
                return true; // Måla bakgrund
            }
        };
        contentPanel.setBackground(Theme.BG);
        contentPanel.setPreferredSize(new Dimension(800, 600));


        // --------- ALL VIEWS ---------
        homeView = new HomeView();
        myDecksView = new MyDecksView(userController, deckController);
        studyView = new StudyView();
        scheduleView = new ScheduleView();
        myAccountView = new MyAccountView();
        signInView = new SignInView();

        navbarView = new NavbarView();
        friendsView = new FriendsView(userController);

        // Lägg till views i CardLayout
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


        // --------- LAYERED PANE (DEN VIKTIGASTE FIXEN!) ---------
        layeredPane = new JLayeredPane() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Theme.BG);      // Tvingar bakgrundsfärg
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        layeredPane.setLayout(new BorderLayout());

        layeredPane.add(contentPanel, BorderLayout.CENTER);
        layeredPane.add(overlayLayer, BorderLayout.CENTER, JLayeredPane.PALETTE_LAYER);

        // Detta behövs inte längre men är okej att ha:
        layeredPane.setBackground(Theme.BG);
    }




    private void layoutComponents() {

        setLayout(new BorderLayout());

        // Navbar at top
        add(navbarView, BorderLayout.NORTH);

        // Friends panel at left
        add(friendsView, BorderLayout.WEST);

        // --- IMPORTANT FIX WRAPPER ---
        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setOpaque(true);
        centerWrapper.setBackground(Theme.BG);
        centerWrapper.add(layeredPane, BorderLayout.CENTER);

        // Main content in center
        add(centerWrapper, BorderLayout.CENTER);
    }


    // -------- PAGE SWITCHING --------
    public void showPage(String pageName) {
        System.out.println("Switching to page: " + pageName);

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
        navbarView.setOnSearch(text -> {
            Integer userId = userController.getLoggedInUserId();

            var results = deckController.searchDecks(userId, text);

            homeView.setDecks(results);
        });


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

    // Force background color recursively on all components
    private void forceBackgroundRecursively(Component comp, Color bg) {
        if (comp instanceof JComponent j) {
            j.setOpaque(true);
            j.setBackground(bg);
        }

        if (comp instanceof Container container) {
            for (Component child : container.getComponents()) {
                forceBackgroundRecursively(child, bg);
            }
        }
    }

}

package view;

import org.flashcard.UiControllers.AppController;
import org.flashcard.UiControllers.UserUIController;
import org.flashcard.UiControllers.DeckUIController;
import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.StudyController;
import org.flashcard.controllers.UserController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final UserUIController userUI;
    private final DeckUIController deckUI;
    private final AppController app;

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

        // UI Controllers (kopplar till backend men UI använder bara dessa)
        this.userUI = new UserUIController(userController);
        this.myDecksView = new MyDecksView();  // ändrad: tar inte in controllers
        this.deckUI = new DeckUIController(deckController, myDecksView);

        this.app = new AppController(userUI, deckUI);

        initComponents();
        layoutComponents();

        // koppla ihop AppController med views
        app.connectViews(this);

        showPage("Home");

        setTitle("Flashcard APP");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        homeView = new HomeView();
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
        friendsView = new FriendsView();
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        add(navbarView, BorderLayout.NORTH);
        add(friendsView, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // navigation
        navbarView.setOnNavigate(this::showPage);
    }

    public void showPage(String page) {
        cardLayout.show(contentPanel, page);

        if (page.equals("MyDecks")) {
            deckUI.reload();   // ladda decks när sidan öppnas
        }

        navbarView.highlight(page);
    }

    // ---- getters (nödvändiga för AppController) ----

    public FriendsView getFriendsView() { return friendsView; }
    public SignInView getSignInView() { return signInView; }
    public MyDecksView getMyDecksView() { return myDecksView; }
}

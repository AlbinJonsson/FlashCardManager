package view;

import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.StudyController;
import org.flashcard.controllers.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class BossFrame extends JFrame {
    private static final Logger log = LoggerFactory.getLogger(BossFrame.class);
    private JPanel contentPanel;
    private UserController userController;
    private StudyController studyController;
    private DeckController deckController;

    private NavbarView navbarView;
    private FriendsView friendsView;
    private CardLayout cardLayout;

    private HomeView homeView;
    private MyDecksView myDecksView;
    private StudyView studyView;
    private ScheduleView scheduleView;
    private MyAccountView myAccountView;
    private SignInView signInView;
    private CreateDeckView createDeckView;
    private CreateFlashcardView createFlashcardView;
    public BossFrame(UserController userController,
                     StudyController studyController,
                     DeckController deckController){
        setTitle("BossFrame - Blank Canvas");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.userController = userController;
        this.studyController = studyController;
        this.deckController = deckController;
        initComponents();
        layoutComponents();
        styleComponents();
        setNavbarListener();
        setButtonListeners();
        loginHandler();
    }
    private void initComponents() {
        cardLayout = new CardLayout();

        contentPanel = new JPanel(cardLayout);
        homeView = new HomeView();
        myDecksView = new MyDecksView(userController, deckController);
        studyView = new StudyView();
        scheduleView = new ScheduleView();
        myAccountView = new MyAccountView();
        signInView = new SignInView();
        createDeckView = new CreateDeckView(deckController, userController);
        createFlashcardView = new CreateFlashcardView(deckController, userController);

        navbarView = new NavbarView();
        friendsView = new FriendsView(userController);
    }
    private void layoutComponents () {
        setLayout(new BorderLayout());
        add(friendsView, BorderLayout.WEST);
        add(navbarView, BorderLayout.NORTH);
        contentPanel.add(homeView, "Home");
        contentPanel.add(myDecksView, "MyDecks");
        contentPanel.add(studyView, "Study");
        contentPanel.add(scheduleView, "Schedule");
        contentPanel.add(myAccountView, "MyAccount");
        contentPanel.add(signInView, "SignIn");
        contentPanel.add(createDeckView, "CreateDeck");
        contentPanel.add(createFlashcardView, "CreateFlashcard");
        add(contentPanel);

    }
    private void setNavbarListener() {
        navbarView.setOnNavigate(this::showPage);
    }
    private void setButtonListeners() {
        myDecksView.setButtonListener(this::showPage);
        createDeckView.setButtonListener(this::showPage);
    }
    private void styleComponents(){
        contentPanel.setOpaque(true);
        contentPanel.setBackground(Theme.BG);
    }
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



    }
    public void loginHandler(){
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
}

package org.flashcard.UiControllers;

import view.MainFrame;
import view.MyDecksView;
import view.FriendsView;
import view.SignInView;

public class AppController {

    private final UserUIController userUI;
    private final DeckUIController deckUI;

    public AppController(
            UserUIController userUI,
            DeckUIController deckUI
    ) {
        this.userUI = userUI;
        this.deckUI = deckUI;

        // När user ändras → DeckUI ska byta user
        userUI.addListener(deckUI::onUserChanged);
    }

    public void connectViews(MainFrame frame) {

        // SIGN IN VIEW → userUIController
        SignInView signIn = frame.getSignInView();
        signIn.setOnSignIn((u, p) -> {
            boolean ok = userUI.login(u, p);
            if (!ok) {
                signIn.showMessage("Invalid username or password.");
                return;
            }
            signIn.clear();
            frame.showPage("Home");
        });

        // FRIENDS VIEW → lyssnar på userUIController
        FriendsView friends = frame.getFriendsView();
        userUI.addListener(friends::onUserChanged);

        // MY DECKS VIEW → deckUI (redan lyssnar på model)
        MyDecksView decks = frame.getMyDecksView();
        // deckUIController skickar DeckDTO → MyDecksView.updateDeckCards()

        // När user ändras → decks ska uppdatera sig
        userUI.addListener(u -> deckUI.reload());
    }

    public UserUIController getUserUI() { return userUI; }
    public DeckUIController getDeckUI() { return deckUI; }
}

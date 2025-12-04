package org.flashcard.UiControllers;

import org.flashcard.controllers.UserController;
import org.flashcard.models.dataclasses.User;
import org.flashcard.UiControllers.Observable;

import java.util.Optional;

public class UserUIController extends Observable<User> {

    private final UserController backend;
    private User currentUser;

    public UserUIController(UserController backend) {
        this.backend = backend;
    }

    public boolean login(String username, String password) {
        boolean ok = backend.login(username, password);
        if (!ok) {
            return false;
        }

        // Hämta User-entity via backend.getAllUsers()
        Optional<User> match = backend.getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();

        currentUser = match.orElse(null);

        if (currentUser != null) {
            backend.setCurrentUser(currentUser);   // synka med befintlig backend
            notify(currentUser);                   // tala om för observers (t.ex. DeckUIController, FriendsView)
            return true;
        }

        return false;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        backend.setCurrentUser(user);
        notify(user);
    }

    public User getCurrentUser() {
        return currentUser;
    }
}

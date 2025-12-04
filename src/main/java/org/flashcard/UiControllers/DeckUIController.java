package org.flashcard.UiControllers;


import org.flashcard.controllers.DeckController;
import org.flashcard.models.dataclasses.User;
import org.flashcard.UiControllers.Observer;
import org.flashcard.UiControllers.dto.DeckDTO;
import org.flashcard.UiControllers.model.DeckModel;
import view.MyDecksView;

import java.util.List;

public class DeckUIController {

    private final DeckModel deckModel;

    public DeckUIController(DeckController backend, MyDecksView view) {
        this.deckModel = new DeckModel(backend);

        // Koppla model → view
        deckModel.addListener(view::updateDeckCards);
    }

    // ⬇⬇⬇ DENNA SAKNAR DU ⬇⬇⬇
    public void addDecksListener(Observer<List<DeckDTO>> listener) {
        deckModel.addListener(listener);
    }
    // ⬆⬆⬆ DEN SKA DU LÄGGA TILL ⬆⬆⬆

    public void onUserChanged(User user) {
        deckModel.setCurrentUser(user);
    }

    public void reload() {
        deckModel.reloadDecks();
    }
}

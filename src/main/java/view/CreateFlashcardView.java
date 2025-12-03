package view;

import org.flashcard.controllers.DeckController;
import org.flashcard.controllers.UserController;

public class CreateFlashcardView extends CreateDeckView{
    private final DeckController deckController;
    private final UserController userController;
    public CreateFlashcardView(DeckController deckController,UserController userController) {
        super(deckController, userController);
        this.deckController = deckController;
        this.userController = userController;

    }

}

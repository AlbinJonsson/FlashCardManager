package view;

import java.awt.*;

public class DeckCardTempController {

    private final DeckCard view;

    public DeckCardTempController(DeckCard view) {
        this.view = view;
    }

    /**
     * Kallas t.ex. när "modellen" uppdaterats.
     * Här matar vi bara in fejkdata för att se vyn.
     */
    public void loadDummyData() {
        int cardCount = 7;
        int trophyCount = 3;
        String title = "Ice Age";
        String tag = "history";
        Color tagColor = new Color(240, 180, 120);

        view.setCardData(cardCount, trophyCount, title, tag, tagColor);
    }
}

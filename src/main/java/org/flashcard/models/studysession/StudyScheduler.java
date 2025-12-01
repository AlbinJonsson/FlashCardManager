package org.flashcard.models.studysession;

import org.flashcard.models.dataclasses.Flashcard;
import org.flashcard.models.ratingstrategy.StrategyFactory;

public class StudyScheduler {
    private final StrategyFactory strategyFactory;

    public StudyScheduler(StrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public boolean isDue(Flashcard card) {
        return true;
        //return card.getCardLearningState().isDueToday();
    }
}

package org.flashcard.models.ratingstrategy;

import org.flashcard.models.dataclasses.Flashcard;

import java.time.LocalDate;

public class StrategyMedium extends BaseIntervalStrategy {
    @Override
    protected double getMultiplier() {
        return 1.5; //Increases lagom
    }
}

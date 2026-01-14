package org.flashcard.models.timer;



public interface CountdownListener {
    void onTick(String countdown);
    void onFinished();
}


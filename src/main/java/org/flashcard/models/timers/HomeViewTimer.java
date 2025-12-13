package org.flashcard.models.timers;


import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HomeViewTimer {
    private final Timer timer;
    private final ArrayList<ActionListener> timerListenerList = new ArrayList<>();
    public HomeViewTimer(ActionListener timerListener) {
        timer = new Timer(1000, timerListener);
        timer.start();
    }
    public void addTimerListener(ActionListener timerListener){
        timerListenerList.add(timerListener);
    }
    public void stopTimer(){
        timer.stop();
    }

}

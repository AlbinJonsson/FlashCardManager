package org.flashcard.UiControllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Observable<T> {

    private final List<Observer<T>> listeners = new ArrayList<>();

    public void addListener(Observer<T> l) {
        if (l != null) {
            listeners.add(l);
        }
    }

    public void removeListener(Observer<T> l) {
        listeners.remove(l);
    }

    public void notify(T data) {
        // kopia för att undvika ConcurrentModification om någon tar bort sig själv
        for (Observer<T> l : List.copyOf(listeners)) {
            l.notify(data);
        }
    }
}

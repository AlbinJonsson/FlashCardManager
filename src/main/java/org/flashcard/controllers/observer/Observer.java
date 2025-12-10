package org.flashcard.controllers.observer;

public interface Observer<T> {
    void notify(T data);
}

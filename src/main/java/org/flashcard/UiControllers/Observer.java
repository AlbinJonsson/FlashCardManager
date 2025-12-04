package org.flashcard.UiControllers;

public interface Observer<T> {
    void notify(T data);
}

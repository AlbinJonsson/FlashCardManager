package org.flashcard.models;

public interface Observer<T> {
    void notify(T data);
}

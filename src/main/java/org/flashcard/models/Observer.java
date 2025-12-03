package org.flashcard.models;

public interface Observer<T> {
    void update(T data);
}

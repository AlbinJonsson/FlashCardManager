package org.Flashcard.repository;

import java.util.*;


public interface IRepository<T> {
    T findById(Long id);
    List<T> findAll();
    void delete(Long id);
}

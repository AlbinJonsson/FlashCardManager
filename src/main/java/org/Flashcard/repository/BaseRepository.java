package org.Flashcard.repository;

import java.lang.reflect.Field;
import java.util.*;
public abstract class BaseRepository<T> implements IRepository<T> {

    protected Map<Long, T> storage = new HashMap<>();
    private long idCounter = 1;

    protected Long nextId() {
        return idCounter++;
    }

    // Ny metod för att hantera reflection och lagring
    protected T setAndStore(T entity) {
        Long id = nextId();
        try {
            // Hämta ID-fältet via reflection (fungerar om T har ett 'id'-fält)
            Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Kunde inte sätta 'id' på entiteten av typ " + entity.getClass().getSimpleName(), e);
        }

        storage.put(id, entity);
        return entity;
    }

    @Override
    public T findById(Long id) {
        return storage.get(id);
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }
}


package org.Flashcard.repository;

import org.Flashcard.model.User;

public class UserRepository extends BaseRepository<User> implements IUserRepository {

    @Override
    public User save(User user) {
        return setAndStore(user);
    }

    @Override
    public User findByName(String name) {
        return storage.values().stream()
                .filter(u -> u.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}

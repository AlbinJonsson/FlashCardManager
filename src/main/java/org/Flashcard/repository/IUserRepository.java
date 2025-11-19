package org.Flashcard.repository;

import org.Flashcard.model.User;
import java.util.List;

public interface IUserRepository extends IRepository<User> {
    User save(User user);
    User findByName(String name);
}
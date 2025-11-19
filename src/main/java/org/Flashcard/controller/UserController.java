package org.Flashcard.controller;

import org.Flashcard.model.User;
import org.Flashcard.repository.IUserRepository;

public class UserController {

    private final IUserRepository users;

    public UserController(IUserRepository users) {
        this.users = users;
    }

    public User register(String name, String passwordHash) {
        User user = new User(name, passwordHash);
        return users.save(user);
    }

    public User login(String name, String passwordHash) {
        User user = users.findByName(name);
        if (user == null) return null;
        if (!user.getPasswordHash().equals(passwordHash)) return null;
        return user;
    }
}

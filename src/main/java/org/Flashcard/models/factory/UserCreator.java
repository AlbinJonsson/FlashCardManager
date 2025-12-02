// 2. UserCreator.java
package org.Flashcard.models.factory;

import org.Flashcard.models.dataClasses.User;

public class UserCreator extends EntityCreator {
    private final String username;
    private final String password;

    // You set the data when you make the creator
    public UserCreator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public User create() {
        return new User(username, password);
    }
}
package org.flashcard.controllers;

import org.flashcard.models.dataclasses.Deck;
import org.flashcard.models.dataclasses.Tag;
import org.flashcard.models.dataclasses.User;
import org.flashcard.repositories.DeckRepository;
import org.flashcard.repositories.TagRepository;
import org.flashcard.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserController {

    private final UserRepository userRepo;
    private final DeckRepository deckRepo;
    private final TagRepository tagRepo;
    private User currentUser;

    public UserController(UserRepository userRepo,
                       DeckRepository deckRepo,
                       TagRepository tagRepo) {
        this.userRepo = userRepo;
        this.deckRepo = deckRepo;
        this.tagRepo = tagRepo;
    }

    public boolean login(String username, String password) {
        Optional<User> optionalUser = userRepo.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return false; // user not found
        }

        User user = optionalUser.get();
        String hashedInput = hashPassword(password);

        return user.getPassword().equals(hashedInput);
    }


    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not supported", e);
        }
    }



    // --- User CRUD ---


    public void setCurrentUser(User user) {
        this.currentUser = user;  // auto-login as this user
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User createUser(String username, String password) {
        if (userRepo.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User(username, password);
        return userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Integer userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User updateUser(Integer userId, String newUsername, String newPassword) {
        User user = getUserById(userId);

        if (newUsername != null && !newUsername.isBlank()) {
            if (!newUsername.equals(user.getUsername()) && userRepo.existsByUsername(newUsername)) {
                throw new IllegalArgumentException("Username already exists");
            }
            user.setUsername(newUsername);
        }

        if (newPassword != null && !newPassword.isBlank()) {
            user.setPassword(newPassword); // assume hashed elsewhere
        }

        return userRepo.save(user);
    }

    public void deleteUser(Integer userId) {
        if (!userRepo.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepo.deleteById(userId); // cascades to decks/tags if JPA cascade set
    }

    // --- User-related queries ---

    public List<Deck> getDecksForUser(Integer userId) {
        getUserById(userId); // ensure user exists
        return deckRepo.findByUserId(userId);
    }

    public List<Tag> getTagsForUser(Integer userId) {
        getUserById(userId); // ensure user exists
        return tagRepo.findByUserId(userId);
    }

    public String getTagText(Integer tagId) {
        return tagRepo.findById(tagId)
                .map(tag -> "Tag: " + tag.getTitle() + ", Color: #" + tag.getColor())
                .orElse("");
    }

    // Placeholder for getting logged-in user ID
    // Method is not completed ???
    public Integer getLoggedInUserId() {
        return null;
    }
}

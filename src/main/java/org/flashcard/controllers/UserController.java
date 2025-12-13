package org.flashcard.controllers;

import org.flashcard.application.dto.DeckDTO;
import org.flashcard.application.dto.TagDTO;
import org.flashcard.application.dto.UserDTO;
import org.flashcard.application.mapper.DeckMapper;
import org.flashcard.application.mapper.TagMapper;
import org.flashcard.application.mapper.UserMapper;
import org.flashcard.models.dataclasses.Deck;
import org.flashcard.models.dataclasses.Tag;
import org.flashcard.models.dataclasses.User;
import org.flashcard.models.services.UserService;
import org.flashcard.repositories.DeckRepository;
import org.flashcard.repositories.TagRepository;
import org.flashcard.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/* We use Spring Data JPA to access the database.
 *
 * This class is annotated with @Service, which tells Spring
 * that it is a service-layer component.
 *
 * Spring automatically detects it and creates a bean in the application context,
 * so it can be injected wherever needed.(see main.java)
 *
 * The @Transactional annotation ensures that no database transactions are left unfinished.
 * It automatically aborts any transactions that result in an error.
 * This allows us to write logic without manually handling database transactions.
 */
@Service
@Transactional
public class UserController {

    private final UserRepository userRepo;
    private final DeckRepository deckRepo;
    private final TagRepository tagRepo;


    private final UserService userService;

    public UserController(UserRepository userRepo,
                          DeckRepository deckRepo,
                          TagRepository tagRepo,
                          UserService userService) {
        this.userRepo = userRepo;
        this.deckRepo = deckRepo;
        this.tagRepo = tagRepo;
        this.userService = userService;
    }

    // ---User CRUD---

    public UserDTO createUser(String username) {
        return userService.createUser(username);
    }


    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    public UserDTO getUserById(Integer userId) {
        return userService.getUserById(userId);
    }

    public void deleteUser(Integer userId) {
        userService.deleteUser(userId);
    }

    public UserDTO getCurrentUser() {
        return userService.getCurrentUser();
    }

    public Integer getCurrentUserId() {
        return userService.getCurrentUserId();
    }

    public void loginByUserId(Integer userId) {
        userService.loginByUserId(userId);
    }

    public TagDTO createTag(Integer userId, String title, String color) {

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Tag title cannot be empty");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Tag tag = new Tag(title.trim(), color, user);
        Tag savedTag = tagRepo.save(tag);
        return TagMapper.toDTO(savedTag);
    }


    // --- User CRUD ---



    // --- User-related queries ---

    public List<DeckDTO> getDecksForUser(Integer userId) {
        if (!userRepo.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }
        List<Deck> decks = deckRepo.findByUserId(userId);

        return DeckMapper.toDTOList(decks);
    }

    public List<TagDTO> getTagsForUser(Integer userId) {
        if (!userRepo.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }
        List<Tag> tags = tagRepo.findByUserId(userId);

        return TagMapper.toDTOList(tags);
    }

    public String getTagText(Integer tagId) {
        return tagRepo.findById(tagId)
                .map(tag -> "Tag: " + tag.getTitle() + ", Color: #" + tag.getColor())
                .orElse("");
    }
}
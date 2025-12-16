package org.flashcard.controllers;


import org.flashcard.application.dto.UserDTO;
import org.flashcard.models.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


/* We use Spring Data JPA to access the database.
 * This class is annotated with @Service, which tells Spring
 * that it is a service-layer component.
 * Spring automatically detects it and creates a bean in the application context,
 * so it can be injected wherever needed.(see main.java)
 * The @Transactional annotation ensures that no database transactions are left unfinished.
 * It automatically aborts any transactions that result in an error.
 * This allows us to write logic without manually handling database transactions.
 */
@Service
@Transactional
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserDTO createUser(String username) {
        return userService.createUser(username);
    }

    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
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

}
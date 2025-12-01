package org.flashcard.repositories;

import org.flashcard.models.dataclasses.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

// Vi använder Spring JPA som är en smart implentation av Repositories. Den kommer med inbyggda
// funtionener som står nedan:

// save(User user)                  - Inserts a new row if the entity is new, or updates an existing row if it has an ID
// findById(ID id)                  - Finds a row in the database by primary key and returns it as Optional<User>
// findAll()                        - Retrieves all rows from the table as a list of User objects
// delete(User user)                - Deletes the corresponding row from the database
// existsById(ID id)                - Checks whether a row exists with the given primary key
// findByUsername(String username)  - Automatically implemented by Spring Data JPA to find a user by username
// existsByUsername(String username)- Automatically implemented to check if a user exists with the given username

    // Find a user by username
    Optional<User> findByUsername(String username);

    // Check if a username exists (needed for UserService)
    boolean existsByUsername(String username);

}

package org.flashcard.models.dataclasses;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Users") // table name in PostgreSQL, now using camelCase
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // matches GENERATED ALWAYS AS IDENTITY
    private Integer id;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, length = 60) // for hashed passwords
    private String password;

    @Column(nullable = false)
    private LocalDate dateCreated = LocalDate.now(); // column name now matches Java field

    // Constructors
    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.dateCreated = LocalDate.now();
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDate getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDate dateCreated) { this.dateCreated = dateCreated; }
}

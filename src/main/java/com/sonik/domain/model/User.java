package com.sonik.domain.model;

import com.sonik.domain.exceptions.IncorrectArgumentException;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table (name = "auth")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    private int Id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserPref preferences;

    @Column(name= "username", nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password_hash;

    @Column(nullable = false)
    private LocalDate creation_date;


    public User() {
    }

    public User(String userName, String email, String password_hash, LocalDate creation_date) throws IncorrectArgumentException {
        setUserName(userName);
        setEmail(email);
        setPassword_hash(password_hash);
        setCreation_date(creation_date);
    }

    public int getId() {
        return Id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) throws IncorrectArgumentException {
        if (userName.isBlank()){
            throw new IncorrectArgumentException(IncorrectArgumentException.NULL_OR_EMPTY_OBJECT);
        }
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IncorrectArgumentException {
        if (email.isBlank()){
            throw new IncorrectArgumentException(IncorrectArgumentException.NULL_OR_EMPTY_OBJECT);
        }
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) throws IncorrectArgumentException {
        if (password_hash.isBlank()){
            throw new IncorrectArgumentException(IncorrectArgumentException.NULL_OR_EMPTY_OBJECT);
        }
        this.password_hash = password_hash;
    }

    public LocalDate getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDate creation_date) throws IncorrectArgumentException {
        if (creation_date == null || creation_date.isAfter(LocalDate.now()))
            throw new IncorrectArgumentException(IncorrectArgumentException.INVALID_DATE);
        this.creation_date = creation_date;
    }

    public UserPref getPreferences() {
        return preferences;
    }

    public void setPreferences(UserPref preferences) {
        if (preferences == null) {
            throw new IllegalArgumentException("Preferences cannot be null");
        }
        this.preferences = preferences;
    }
}

package com.sonik.domain.model;

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

    @Column(name= "username", nullable = false)
    private String userName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password_hash;

    @Column(nullable = false)
    private LocalDate creation_date;


    public User() {
    }

    public User(String userName, String email, String password_hash, LocalDate creation_date) {
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

    public void setUserName(String userName) {
        if (userName.isBlank()){
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.isBlank()){
            throw new IllegalArgumentException("Email cannot be empty");
        }
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        if (password_hash.isBlank()){
            throw new IllegalArgumentException("Password hash cannot be empty");
        }
        this.password_hash = password_hash;
    }

    public LocalDate getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(LocalDate creation_date) {
        if (creation_date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Creation Date cannot be in the future");
        } else if (creation_date == null){
            throw new IllegalArgumentException("Creation Date cannot be null");
        }
        this.creation_date = creation_date;
    }
}

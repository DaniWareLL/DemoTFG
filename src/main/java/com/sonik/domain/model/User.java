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

    /**
     * Creates a User.
     * @param userName      The username to assign
     * @param email         The email to assign
     * @param password_hash The hashed password to assign
     * @param creation_date The account creation date
     * @throws IncorrectArgumentException <ul>
     *     <li>{@link IncorrectArgumentException.ErrorType#EMPTY_USERNAME} If userName is blank</li>
     *     <li>{@link IncorrectArgumentException.ErrorType#EMPTY_EMAIL} If email is blank</li>
     *     <li>{@link IncorrectArgumentException.ErrorType#INVALID_EMAIL} If email format is invalid</li>
     *     <li>{@link IncorrectArgumentException.ErrorType#EMPTY_PASSWORD} If password_hash is blank</li>
     *     <li>{@link IncorrectArgumentException.ErrorType#INVALID_DATE} If creation_date is null or in the future</li>
     * </ul>
     */
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

    /**
     * Sets the username of this user.
     * @param userName The username to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#EMPTY_USERNAME} If userName is blank
     */
    public void setUserName(String userName) throws IncorrectArgumentException {
        if (userName.isBlank()){
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.EMPTY_USERNAME);
        }
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of this user. Must follow the format {@code local@domain.tld}.
     * @param email The email to assign
     * @throws IncorrectArgumentException <ul>
     *     <li>{@link IncorrectArgumentException.ErrorType#EMPTY_EMAIL} If email is blank</li>
     *     <li>{@link IncorrectArgumentException.ErrorType#INVALID_EMAIL} If email format is invalid</li>
     * </ul>
     */
    public void setEmail(String email) throws IncorrectArgumentException {
        if (email.isBlank()){
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.EMPTY_EMAIL);
        }
        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.INVALID_EMAIL);
        }
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    /**
     * Sets the hashed password of this user.
     * @param password_hash The hashed password to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#EMPTY_PASSWORD} If password_hash is blank
     */
    public void setPassword_hash(String password_hash) throws IncorrectArgumentException {
        if (password_hash.isBlank()){
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.EMPTY_PASSWORD);
        }
        this.password_hash = password_hash;
    }

    public LocalDate getCreation_date() {
        return creation_date;
    }

    /**
     * Sets the account creation date of this user.
     * @param creation_date The date to assign
     * @throws IncorrectArgumentException
     *     {@link IncorrectArgumentException.ErrorType#INVALID_DATE} If creation_date is null or in the future
     */
    public void setCreation_date(LocalDate creation_date) throws IncorrectArgumentException {
        if (creation_date == null || creation_date.isAfter(LocalDate.now()))
            throw new IncorrectArgumentException(IncorrectArgumentException.ErrorType.INVALID_DATE);
        this.creation_date = creation_date;
    }

    public UserPref getPreferences() {
        return preferences;
    }

    /**
     * Sets the preferences of this user.
     * @param preferences The UserPref to assign
     * @throws IllegalArgumentException If preferences is null
     */
    public void setPreferences(UserPref preferences) {
        if (preferences == null) {
            throw new IllegalArgumentException("Preferences cannot be null");
        }
        this.preferences = preferences;
    }
}

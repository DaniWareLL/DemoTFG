package com.sonik.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * This is a placeholder, JPA entities are auto-generated
 */
@Entity
public class User {
    @Id
    private Long id;
    private String name;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    // User data
}

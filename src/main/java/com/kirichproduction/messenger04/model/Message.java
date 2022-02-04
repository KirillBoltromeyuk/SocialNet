package com.kirichproduction.messenger04.model;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "text")
    private String text;
    @Column(name = "author_username")
    private String authorUsername;
    @Column(name = "destination_username")
    private String destinationUsername;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public String getDestinationUsername() {
        return destinationUsername;
    }

    public void setDestinationUsername(String destinationUsername) {
        this.destinationUsername = destinationUsername;
    }

    public Message() {
    }

    public Message(String text, String authorUsername, String destinationUsername) {
        this.text = text;
        this.authorUsername = authorUsername;
        this.destinationUsername = destinationUsername;
    }
}

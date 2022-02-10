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
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "chat_id")
    private Long chatId;

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

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

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Message() {
    }

    public Message(String text, Long authorId, Long chatId) {
        this.text = text;
        this.authorId = authorId;
        this.chatId = chatId;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id", insertable=false, updatable=false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "chat_id", referencedColumnName = "id", insertable=false, updatable=false)
    private Chat chat;

    public Chat getChat() {
        return chat;
    }
}

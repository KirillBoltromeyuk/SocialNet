package com.kirichproduction.messenger04.model;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "text")
    private String text;
    @Column(name = "author_id")
    private Long authorId;




    public Long getAuthorId() {
        return authorId;
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


    public Post(String text, Long authorId) {
        this.text = text;
        this.authorId = authorId;
    }

    public Post() {
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
}

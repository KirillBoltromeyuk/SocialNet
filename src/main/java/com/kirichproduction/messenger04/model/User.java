package com.kirichproduction.messenger04.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Email should not de empty")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Name should not de empty")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Lastname should not de empty")
    @Size(min = 2, max = 50, message = "Lastname should be between 2 and 100 characters")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Password should not de empty")
    @Size(min = 6, max = 100, message = "Password should be between 6 and 100 characters")
    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "image_id")
    private Long imageId;

    @Transient
    private String passwordConfirm;

    @Transient
    private boolean passwordCheck = true;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public boolean isPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(boolean passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Transient
    public String getImagePath() {
        if (imageId == null) return null;

        return "/images/"+ image.getImageName();
    }

    public User() {
    }

    public User(String email, String firstName, String lastName, String password, Role role, Status status) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="image_id", referencedColumnName = "id", insertable=false, updatable=false)
    private Image image;

    @OneToMany( mappedBy = "user")
    private Collection<Post> posts;

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPost(Collection<Post> posts) {
        this.posts = posts;
    }

    @OneToMany(mappedBy = "user")
    private Collection<Message> messages;
}

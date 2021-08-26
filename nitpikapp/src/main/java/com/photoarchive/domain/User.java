package com.photoarchive.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "users")

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_name", unique=true)
    private String username;
    @Column(unique = true)
    private String email;
    private String firstName;
    private String surname;
    private String password;
    private boolean isEnabled;

    @OneToOne(mappedBy = "user")
    private Token token;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Photo> photos = new HashSet<>();
    @ManyToMany(mappedBy = "user",cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<Photo> photosShare = new HashSet<>();
    public User(Long id, String username, String email, String firstName, String surname, String password, boolean isEnabled, Token token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
        this.isEnabled = isEnabled;
        this.token = token;
    }

    public User(String username, String email, String firstName, String surname, String password, boolean isEnabled) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
        this.isEnabled = isEnabled;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<Photo> getPhotosShare() {
        return photosShare;
    }

    public void setPhotosShare(Set<Photo> photosShare) {
        this.photosShare = photosShare;
    }

}

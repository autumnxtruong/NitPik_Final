package com.photoarchive.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photo_id;
    private String url;

    @ManyToMany  (cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "photos_tags", joinColumns = @JoinColumn(name = "photo_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();
    @ManyToOne
    @JoinTable(name = "photos_user", joinColumns = @JoinColumn(name = "photo_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "photos_shared", joinColumns = @JoinColumn(name = "photo_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> usersShare = new HashSet<>();
    private boolean shared;
    public Long getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(Long photo_id) {
        this.photo_id = photo_id;
    }

    public User getUser() {
        return user;
    }
    public String getUsername() {
        return user.getUsername();
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Photo(User user) {
        this.user = user;
    }
    public Photo( ) {

    }

    public Photo(Long photo_id, String url, Set<Tag> tags, User user) {
        this.photo_id = photo_id;
        this.url = url;
        this.tags = tags;
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public Set<User> getUsersShare() {
        return usersShare;
    }

    public void setUsersShare(Set<User> usersShare) {
        this.usersShare = usersShare;
    }
}

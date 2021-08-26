package com.photoarchive.models;

import com.photoarchive.annotations.Email;
import com.photoarchive.annotations.MatchingPassword;
import com.photoarchive.annotations.StrongPassword;
import com.photoarchive.validators.MatchablePasswords;
import lombok.Data;

@Data
@MatchingPassword
public class UserDTO implements MatchablePasswords {
    private String username;
    @Email
    private String email;
    private String firstName;
    private String surname;
    @StrongPassword
    private String password;
    private String matchingPassword;

    public String getUsername() {
        return username;
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

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}

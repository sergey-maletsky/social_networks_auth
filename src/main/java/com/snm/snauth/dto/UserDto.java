package com.snm.snauth.dto;

import java.io.Serializable;

public class UserDto implements Serializable {

    private String socialMediaId;

    private String email;
    
    private String firstName;
    
    private String lastName;

    public UserDto() {
    }

    public UserDto(String socialMediaId, String email, String firstName, String lastName) {
        this.socialMediaId = socialMediaId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getSocialMediaId() {
        return socialMediaId;
    }

    public void setSocialMediaId(String socialMediaId) {
        this.socialMediaId = socialMediaId;
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
}

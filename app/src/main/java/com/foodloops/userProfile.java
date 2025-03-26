package com.foodloops;

/**
 * Model class representing a user's profile in the FoodLoops app.
 * This class is used to display and manage user profile information.
 */
public class userProfile {
    private String name;
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private String address;
    private String bio;
    private String profileImageUrl;

    // Default constructor for Firebase
    public userProfile() {
    }

    // Constructor with all fields
    public userProfile(String name, String fullName, String email, String phone,
                       String gender, String address, String bio, String profileImageUrl) {
        this.name = name;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.address = address;
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}

package com.foodloops;

/**
 * Model class representing a product category in the FoodLoops app.
 */
public class Category {
    private String id;
    private String name;
    private String iconName;

    // Constructor
    public Category() {
        // Default constructor for Firebase
    }

    public Category(String id, String name, String iconName) {
        this.id = id;
        this.name = name;
        this.iconName = iconName;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}

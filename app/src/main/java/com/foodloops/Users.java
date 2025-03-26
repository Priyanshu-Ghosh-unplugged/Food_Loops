package com.foodloops;


import java.util.List;
import java.util.Map;

/**
 * Model class representing a user in the FoodLoops app.
 */
public class Users {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String profileImageUrl;
    private String gender;
    private List<String> favoritedProductIds;
    private Map<String, CartItem> cart;
    private ImpactStats impactStats;

    // Constructor
    public Users() {
        // Default constructor for Firebase
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getFavoritedProductIds() {
        return favoritedProductIds;
    }

    public void setFavoritedProductIds(List<String> favoritedProductIds) {
        this.favoritedProductIds = favoritedProductIds;
    }

    public Map<String, CartItem> getCart() {
        return cart;
    }

    public void setCart(Map<String, CartItem> cart) {
        this.cart = cart;
    }

    public ImpactStats getImpactStats() {
        return impactStats;
    }

    public void setImpactStats(ImpactStats impactStats) {
        this.impactStats = impactStats;
    }

    /**
     * Helper class for cart items
     */
    public static class CartItem {
        private String productId;
        private int quantity;

        public CartItem() {
            // Default constructor
        }

        public CartItem(String productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    /**
     * Helper class for impact statistics
     */
    public static class ImpactStats {
        private int itemsSaved;
        private double moneySaved;
        private double co2Saved;

        public ImpactStats() {
            // Default constructor
        }

        public ImpactStats(int itemsSaved, double moneySaved, double co2Saved) {
            this.itemsSaved = itemsSaved;
            this.moneySaved = moneySaved;
            this.co2Saved = co2Saved;
        }

        public int getItemsSaved() {
            return itemsSaved;
        }

        public void setItemsSaved(int itemsSaved) {
            this.itemsSaved = itemsSaved;
        }

        public double getMoneySaved() {
            return moneySaved;
        }

        public void setMoneySaved(double moneySaved) {
            this.moneySaved = moneySaved;
        }

        public double getCo2Saved() {
            return co2Saved;
        }

        public void setCo2Saved(double co2Saved) {
            this.co2Saved = co2Saved;
        }
    }
}
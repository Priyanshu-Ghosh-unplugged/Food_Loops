package com.foodloops;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for Firebase operations.
 */
public class Firebaseutils {

    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Add a product to the user's cart
     *
     * @param userId The ID of the user
     * @param productId The ID of the product to add
     * @param quantity The quantity to add
     * @return Task to monitor the operation
     */
    public static Task<Void> addToCart(String userId, String productId, int quantity) {
        Map<String, Object> cartItem = new HashMap<>();
        cartItem.put("productId", productId);
        cartItem.put("quantity", quantity);

        return db.collection("users")
                .document(userId)
                .collection("cart")
                .document(productId)
                .set(cartItem);
    }

    /**
     * Update the quantity of a product in the user's cart
     *
     * @param userId The ID of the user
     * @param productId The ID of the product
     * @param newQuantity The new quantity
     * @return Task to monitor the operation
     */
    public static Task<Void> updateCartItemQuantity(String userId, String productId, int newQuantity) {
        return db.collection("users")
                .document(userId)
                .collection("cart")
                .document(productId)
                .update("quantity", newQuantity);
    }

    /**
     * Remove a product from the user's cart
     *
     * @param userId The ID of the user
     * @param productId The ID of the product to remove
     * @return Task to monitor the operation
     */
    public static Task<Void> removeFromCart(String userId, String productId) {
        return db.collection("users")
                .document(userId)
                .collection("cart")
                .document(productId)
                .delete();
    }

    /**
     * Add a product to the user's favorites
     *
     * @param userId The ID of the user
     * @param productId The ID of the product to favorite
     * @return Task to monitor the operation
     */
    public static Task<Void> addToFavorites(String userId, String productId) {
        return db.collection("users")
                .document(userId)
                .update("favoritedProductIds", FieldValue.arrayUnion(productId));
    }

    /**
     * Remove a product from the user's favorites
     *
     * @param userId The ID of the user
     * @param productId The ID of the product to unfavorite
     * @return Task to monitor the operation
     */
    public static Task<Void> removeFromFavorites(String userId, String productId) {
        return db.collection("users")
                .document(userId)
                .update("favoritedProductIds", FieldValue.arrayRemove(productId));
    }

    /**
     * Update product stock after a purchase
     *
     * @param productId The ID of the product
     * @param quantityPurchased The quantity purchased
     * @return Task to monitor the operation
     */
    public static Task<Void> updateProductStock(String productId, int quantityPurchased) {
        return db.collection("products")
                .document(productId)
                .update("stockRemaining", FieldValue.increment(-quantityPurchased));
    }

    /**
     * Update user impact statistics after a purchase
     *
     * @param userId The ID of the user
     * @param itemsSaved Number of items saved
     * @param moneySaved Amount of money saved
     * @param co2Saved Amount of CO2 saved
     * @return Task to monitor the operation
     */
    public static Task<Void> updateUserImpactStats(
            String userId, int itemsSaved, double moneySaved, double co2Saved) {

        Map<String, Object> updates = new HashMap<>();
        updates.put("impactStats.itemsSaved", FieldValue.increment(itemsSaved));
        updates.put("impactStats.moneySaved", FieldValue.increment(moneySaved));
        updates.put("impactStats.co2Saved", FieldValue.increment(co2Saved));

        return db.collection("users")
                .document(userId)
                .update(updates);
    }
}

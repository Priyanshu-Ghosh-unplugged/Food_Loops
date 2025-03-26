package com.foodloops;

import java.util.Date;

/**
 * Utility class for calculating dynamic prices based on expiry dates.
 */
public class PricingAlgorithm {

    /**
     * Calculate discounted price based on linear decay for the last 5% of product life
     *
     * @param originalPrice Original product price
     * @param minPrice Minimum allowed price (floor)
     * @param expiryDate Product expiry date
     * @param manufacturingDate Product manufacturing date (for shelf life calculation)
     * @return The calculated discounted price
     */
    public static double calculateDynamicPrice(
            double originalPrice,
            double minPrice,
            Date expiryDate,
            Date manufacturingDate) {

        if (originalPrice <= 0 || minPrice <= 0 || expiryDate == null || manufacturingDate == null) {
            return originalPrice;
        }

        Date now = new Date();

        // If already expired, return minimum price
        if (now.after(expiryDate)) {
            return minPrice;
        }

        // Calculate total shelf life and remaining shelf life
        long totalShelfLifeMs = expiryDate.getTime() - manufacturingDate.getTime();
        double totalShelfLifeDays = totalShelfLifeMs / (1000.0 * 60 * 60 * 24);

        long remainingShelfLifeMs = expiryDate.getTime() - now.getTime();
        double remainingShelfLifeDays = remainingShelfLifeMs / (1000.0 * 60 * 60 * 24);

        // Calculate what percentage of shelf life is remaining
        double percentageRemaining = (remainingShelfLifeDays / totalShelfLifeDays) * 100;

        // Start discount when 5% of shelf life remains
        if (percentageRemaining > 5) {
            return originalPrice;
        }

        // Linear decay from original price to minimum price
        // as time progresses from 5% remaining to 0%
        double discountPercentage = (5 - percentageRemaining) / 5;
        double priceRange = originalPrice - minPrice;
        double discount = priceRange * discountPercentage;

        // Apply discount and round to 2 decimal places
        double discountedPrice = originalPrice - discount;
        return Math.round(discountedPrice * 100) / 100.0;
    }

    /**
     * Calculate the estimated savings based on original and discounted price
     *
     * @param originalPrice Original product price
     * @param discountedPrice Discounted product price
     * @return An array with [amountSaved, percentageSaved]
     */
    public static double[] calculateSavings(double originalPrice, double discountedPrice) {
        double amountSaved = originalPrice - discountedPrice;
        double percentageSaved = (amountSaved / originalPrice) * 100;

        return new double[] {
                Math.round(amountSaved * 100) / 100.0,
                Math.round(percentageSaved)
        };
    }

    /**
     * Format the time remaining until expiry in a human-readable format
     *
     * @param expiryDate The product expiry date
     * @return Formatted string showing time remaining
     */
    public static String formatTimeRemaining(Date expiryDate) {
        Date now = new Date();
        long timeRemainingMs = expiryDate.getTime() - now.getTime();

        // Already expired
        if (timeRemainingMs <= 0) {
            return "Expired";
        }

        // Convert to hours, minutes
        long hoursRemaining = timeRemainingMs / (1000 * 60 * 60);
        long minutesRemaining = (timeRemainingMs % (1000 * 60 * 60)) / (1000 * 60);

        if (hoursRemaining > 48) {
            long daysRemaining = hoursRemaining / 24;
            return daysRemaining + " days";
        } else if (hoursRemaining > 0) {
            if (minutesRemaining > 0) {
                return hoursRemaining + " hours " + minutesRemaining + " mins";
            }
            return hoursRemaining + " hours";
        } else {
            return minutesRemaining + " minutes";
        }
    }

    /**
     * Calculate environmental impact of saving this product
     *
     * @param weightGrams Weight of the product in grams
     * @return Estimated CO2 savings in kg
     */
    public static double calculateEnvironmentalImpact(double weightGrams) {
        // Rough estimate: each kg of food saved prevents ~2.5kg CO2 equivalent
        return (weightGrams / 1000) * 2.5;
    }
}
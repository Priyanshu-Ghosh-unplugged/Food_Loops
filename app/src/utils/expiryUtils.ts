
/**
 * Calculate discounted price based on expiry date
 * 
 * @param originalPrice - The original price of the product
 * @param minPrice - The minimum allowed price (floor)
 * @param expiryDate - The product expiry date
 * @param thresholdDays - Number of days before expiry when discount starts applying
 * @returns The calculated discounted price
 */
export const calculateDiscountedPrice = (
  originalPrice: number,
  minPrice: number,
  expiryDate: Date,
  thresholdDays: number = 5
): number => {
  const now = new Date();
  const timeRemaining = expiryDate.getTime() - now.getTime();
  const daysRemaining = timeRemaining / (1000 * 60 * 60 * 24);
  
  // If product is expired, return minimum price
  if (daysRemaining < 0) {
    return minPrice;
  }
  
  // If product is beyond threshold days, return original price
  if (daysRemaining > thresholdDays) {
    return originalPrice;
  }
  
  // Calculate price using linear interpolation
  // As time approaches expiry, price approaches minPrice
  const discount = (thresholdDays - daysRemaining) / thresholdDays;
  const discountAmount = (originalPrice - minPrice) * discount;
  const discountedPrice = originalPrice - discountAmount;
  
  // Round to 2 decimal places and ensure it's not below minimum price
  return Math.max(Math.round(discountedPrice * 100) / 100, minPrice);
};

/**
 * Format the expiry date for display
 * 
 * @param expiryDate - The product expiry date
 * @returns Formatted string based on proximity to expiry
 */
export const formatExpiryDate = (expiryDate: Date): string => {
  const now = new Date();
  const diffTime = expiryDate.getTime() - now.getTime();
  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
  
  if (diffDays < 0) {
    return "Expired";
  } else if (diffDays === 0) {
    return "Expires Today";
  } else if (diffDays === 1) {
    return "Expires Tomorrow";
  } else if (diffDays <= 5) {
    return `Expires in ${diffDays} days`;
  } else {
    // Format date as "15 Jun 2023"
    return expiryDate.toLocaleDateString('en-US', {
      day: 'numeric',
      month: 'short',
      year: 'numeric'
    });
  }
};

/**
 * Calculate expiry percentage (how much time has passed)
 * 
 * @param expiryDate - The product expiry date
 * @param shelfLifeDays - Total shelf life in days (for percentage calculation)
 * @returns Percentage of shelf life remaining (0-100)
 */
export const calculateExpiryPercentage = (
  expiryDate: Date,
  shelfLifeDays: number = 30
): number => {
  const now = new Date();
  const timeRemaining = expiryDate.getTime() - now.getTime();
  const daysRemaining = timeRemaining / (1000 * 60 * 60 * 24);
  
  // Calculate percentage remaining
  const percentRemaining = (daysRemaining / shelfLifeDays) * 100;
  
  // Bound between 0 and 100
  return Math.max(0, Math.min(100, percentRemaining));
};

/**
 * Get urgency level based on expiry date
 * 
 * @param expiryDate - The product expiry date
 * @returns 'high', 'medium', 'low' based on days remaining
 */
export const getExpiryUrgency = (expiryDate: Date): 'high' | 'medium' | 'low' => {
  const now = new Date();
  const diffTime = expiryDate.getTime() - now.getTime();
  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
  
  if (diffDays <= 2) {
    return 'high';
  } else if (diffDays <= 5) {
    return 'medium';
  } else {
    return 'low';
  }
};

/**
 * Calculate savings amount
 * 
 * @param originalPrice - The original price of the product
 * @param discountedPrice - The discounted price
 * @returns The amount saved
 */
export const calculateSavings = (
  originalPrice: number,
  discountedPrice: number
): number => {
  return Math.round((originalPrice - discountedPrice) * 100) / 100;
};

/**
 * Calculate environmental impact
 * 
 * @param itemsCount - Number of items purchased
 * @returns Estimated CO2 savings in kg
 */
export const calculateEnvironmentalImpact = (itemsCount: number): number => {
  // Rough estimate: each saved food item prevents ~2.5kg CO2 equivalent
  return itemsCount * 2.5;
};
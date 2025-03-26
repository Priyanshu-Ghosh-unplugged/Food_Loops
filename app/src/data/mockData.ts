
import { Product, Store, Category, User } from '../types';

// Mock Stores
export const stores: Store[] = [
  {
    id: '1',
    name: 'Fresh Mart',
    address: '123 Grocery Lane, Delhi',
    distance: 0.8,
    logo: '/stores/freshmart.png',
    rating: 4.5
  },
  {
    id: '2',
    name: 'Super Foods',
    address: '456 Main St, Mumbai',
    distance: 1.2,
    logo: '/stores/superfoods.png',
    rating: 4.2
  },
  {
    id: '3',
    name: 'Green Grocers',
    address: '789 Vegetable Rd, Bangalore',
    distance: 2.5,
    logo: '/stores/greengrocer.png',
    rating: 4.7
  },
  {
    id: '4',
    name: 'Daily Fresh',
    address: '101 Market St, Hyderabad',
    distance: 0.5,
    logo: '/stores/dailyfresh.png',
    rating: 4.3
  }
];

// Categories
export const categories: Category[] = [
  {
    id: 'all',
    name: 'All',
    icon: 'shopping-basket'
  },
  {
    id: 'dairy',
    name: 'Dairy',
    icon: 'milk'
  },
  {
    id: 'produce',
    name: 'Produce',
    icon: 'apple'
  },
  {
    id: 'bakery',
    name: 'Bakery',
    icon: 'bread'
  },
  {
    id: 'meat',
    name: 'Meat',
    icon: 'drumstick'
  },
  {
    id: 'grocery',
    name: 'Grocery',
    icon: 'shopping-cart'
  }
];

// Today's date for reference
const today = new Date();

// Helper to create a date X days from now
const daysFromNow = (days: number) => {
  const date = new Date(today);
  date.setDate(date.getDate() + days);
  return date;
};

// Mock Products
export const products: Product[] = [
  {
    id: '1',
    name: 'Organic Milk',
    category: 'dairy',
    originalPrice: 30,
    minPrice: 18,
    expiryDate: daysFromNow(2),
    shelfLifeDays: 14,
    image: '/products/milk.jpg',
    store: stores[0],
    description: 'Fresh organic milk from local farms. Pasteurized and homogenized.',
    quantity: 500,
    unit: 'ml',
    nutritionalInfo: {
      calories: 150,
      protein: 8,
      carbs: 12,
      fat: 8
    },
    tags: ['organic', 'vegetarian', 'local']
  },
  {
    id: '2',
    name: 'Whole Wheat Bread',
    category: 'bakery',
    originalPrice: 45,
    minPrice: 25,
    expiryDate: daysFromNow(1),
    shelfLifeDays: 7,
    image: '/products/bread.jpg',
    store: stores[1],
    description: 'Freshly baked whole wheat bread with no preservatives.',
    quantity: 400,
    unit: 'g',
    nutritionalInfo: {
      calories: 280,
      protein: 10,
      carbs: 54,
      fat: 2
    },
    tags: ['whole grain', 'no preservatives']
  },
  {
    id: '3',
    name: 'Fresh Tomatoes',
    category: 'produce',
    originalPrice: 40,
    minPrice: 20,
    expiryDate: daysFromNow(3),
    shelfLifeDays: 10,
    image: '/products/tomatoes.jpg',
    store: stores[2],
    description: 'Ripe, juicy tomatoes from local farms. Perfect for salads and cooking.',
    quantity: 500,
    unit: 'g',
    nutritionalInfo: {
      calories: 90,
      protein: 1,
      carbs: 19,
      fat: 0.2
    },
    tags: ['organic', 'local', 'fresh']
  },
  {
    id: '4',
    name: 'Chicken Breast',
    category: 'meat',
    originalPrice: 150,
    minPrice: 90,
    expiryDate: daysFromNow(1),
    shelfLifeDays: 5,
    image: '/products/chicken.jpg',
    store: stores[0],
    description: 'Boneless, skinless chicken breast. Hormone and antibiotic-free.',
    quantity: 500,
    unit: 'g',
    nutritionalInfo: {
      calories: 165,
      protein: 31,
      carbs: 0,
      fat: 3.6
    },
    tags: ['protein', 'no antibiotics']
  },
  {
    id: '5',
    name: 'Greek Yogurt',
    category: 'dairy',
    originalPrice: 60,
    minPrice: 40,
    expiryDate: daysFromNow(4),
    shelfLifeDays: 21,
    image: '/products/yogurt.jpg',
    store: stores[3],
    description: 'Creamy Greek yogurt. High in protein and probiotics.',
    quantity: 400,
    unit: 'g',
    nutritionalInfo: {
      calories: 100,
      protein: 10,
      carbs: 6,
      fat: 0.4
    },
    tags: ['probiotic', 'high protein', 'low fat']
  },
  {
    id: '6',
    name: 'Fresh Spinach',
    category: 'produce',
    originalPrice: 30,
    minPrice: 15,
    expiryDate: daysFromNow(2),
    shelfLifeDays: 7,
    image: '/products/spinach.jpg',
    store: stores[2],
    description: 'Crisp, dark green spinach leaves. Packed with nutrients.',
    quantity: 200,
    unit: 'g',
    nutritionalInfo: {
      calories: 23,
      protein: 2.9,
      carbs: 3.6,
      fat: 0.4
    },
    tags: ['organic', 'leafy greens', 'iron-rich']
  },
  {
    id: '7',
    name: 'Whole Grain Pasta',
    category: 'grocery',
    originalPrice: 80,
    minPrice: 50,
    expiryDate: daysFromNow(30),
    shelfLifeDays: 365,
    image: '/products/pasta.jpg',
    store: stores[1],
    description: 'Nutritious whole grain pasta made from durum wheat.',
    quantity: 500,
    unit: 'g',
    nutritionalInfo: {
      calories: 350,
      protein: 14,
      carbs: 74,
      fat: 2
    },
    tags: ['whole grain', 'high fiber']
  },
  {
    id: '8',
    name: 'Eggs (6-pack)',
    category: 'dairy',
    originalPrice: 60,
    minPrice: 40,
    expiryDate: daysFromNow(7),
    shelfLifeDays: 30,
    image: '/products/eggs.jpg',
    store: stores[0],
    description: 'Farm fresh eggs from free-range hens.',
    quantity: 6,
    unit: 'pcs',
    nutritionalInfo: {
      calories: 70,
      protein: 6,
      carbs: 0.6,
      fat: 5
    },
    tags: ['free-range', 'protein-rich']
  },
  {
    id: '9',
    name: 'Ripe Bananas',
    category: 'produce',
    originalPrice: 40,
    minPrice: 15,
    expiryDate: daysFromNow(3),
    shelfLifeDays: 7,
    image: '/products/bananas.jpg',
    store: stores[3],
    description: 'Sweet, ripe bananas. Perfect for snacking or baking.',
    quantity: 6,
    unit: 'pcs',
    nutritionalInfo: {
      calories: 105,
      protein: 1.3,
      carbs: 27,
      fat: 0.4
    },
    tags: ['quick energy', 'potassium-rich']
  },
  {
    id: '10',
    name: 'Cottage Cheese',
    category: 'dairy',
    originalPrice: 70,
    minPrice: 45,
    expiryDate: daysFromNow(4),
    shelfLifeDays: 14,
    image: '/products/cottage-cheese.jpg',
    store: stores[1],
    description: 'Creamy cottage cheese. High in protein and calcium.',
    quantity: 200,
    unit: 'g',
    nutritionalInfo: {
      calories: 120,
      protein: 14,
      carbs: 3,
      fat: 5
    },
    tags: ['high protein', 'calcium-rich']
  }
];

// Mock User
export const mockUser: User = {
  id: 'user1',
  name: 'Arun Singh',
  email: 'arun.singh@example.com',
  savedItems: ['1', '5', '9'],
  cart: [
    { productId: '3', quantity: 1 },
    { productId: '8', quantity: 2 }
  ],
  impactStats: {
    itemsSaved: 27,
    moneySaved: 845,
    co2Saved: 67.5
  }
};

// Helper function to get a product by ID
export const getProductById = (id: string): Product | undefined => {
  return products.find(product => product.id === id);
};

// Helper function to get products by category
export const getProductsByCategory = (categoryId: string): Product[] => {
  if (categoryId === 'all') {
    return products;
  }
  return products.filter(product => product.category === categoryId);
};

// Helper function to get products in user's cart
export const getCartProducts = (): { product: Product, quantity: number }[] => {
  return mockUser.cart.map(cartItem => {
    const product = getProductById(cartItem.productId);
    if (!product) throw new Error(`Product not found: ${cartItem.productId}`);
    return {
      product,
      quantity: cartItem.quantity
    };
  });
};
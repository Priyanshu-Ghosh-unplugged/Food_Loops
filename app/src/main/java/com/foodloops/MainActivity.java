package com.foodloops;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodloops.R;
import com.foodloops.CategoryAdapter;
import com.foodloops.ProductAdapter;
import com.foodloops.Product;
import com.foodloops.Firebaseutils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        CategoryAdapter.OnCategoryClickListener,
        ProductAdapter.OnProductClickListener {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private RecyclerView categoriesRecyclerView;
    private RecyclerView productsRecyclerView;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private EditText searchEditText;
    private ImageView profileIcon;
    private List<Product> allProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        initializeUI();
        setupBottomNavigation();
        loadCategories();
        loadProducts("all"); // Load all products initially
    }

    private void initializeUI() {
        // Find views
        categoriesRecyclerView = findViewById(R.id.categories_recycler_view);
        productsRecyclerView = findViewById(R.id.products_recycler_view);
        searchEditText = findViewById(R.id.search_edit_text);
        profileIcon = findViewById(R.id.profile_icon);

        // Set up RecyclerViews
        categoriesRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize adapters
        categoryAdapter = new CategoryAdapter(this, new ArrayList<>(), this);
        productAdapter = new ProductAdapter(this, new ArrayList<>(), this);

        categoriesRecyclerView.setAdapter(categoryAdapter);
        productsRecyclerView.setAdapter(productAdapter);

        // Set up click listeners
        profileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_categories) {
                // Handle categories navigation
                return true;
            } else if (itemId == R.id.nav_favorites) {
                // Handle favorites navigation
                return true;
            } else if (itemId == R.id.nav_profile) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    private void loadCategories() {
        // In a real app, this would load from Firebase
        List<CategoryItem> categories = new ArrayList<>();
        categories.add(new CategoryItem("all", "All", R.drawable.ic_all));
        categories.add(new CategoryItem("fruits", "Fruits", R.drawable.ic_fruits));
        categories.add(new CategoryItem("vegetables", "Vegetables", R.drawable.ic_vegetables));
        categories.add(new CategoryItem("dairy", "Dairy", R.drawable.ic_dairy));
        categories.add(new CategoryItem("bakery", "Bakery", R.drawable.ic_bakery));
        categories.add(new CategoryItem("meat", "Meat", R.drawable.ic_meat));

        categoryAdapter.updateCategories(categories);
    }

    private void loadProducts(String categoryId) {
        // Show loading indicator
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

        Query query;
        if ("all".equals(categoryId)) {
            query = db.collection("products");
        } else {
            query = db.collection("products").whereEqualTo("category", categoryId);
        }

        query.get().addOnCompleteListener(task -> {
            findViewById(R.id.progress_bar).setVisibility(View.GONE);

            if (task.isSuccessful()) {
                List<Product> products = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Product product = document.toObject(Product.class);
                    product.setId(document.getId());
                    products.add(product);
                }

                allProducts = products;
                productAdapter.updateProducts(products);

                // Update empty state
                updateEmptyState(products.isEmpty());
            } else {
                Toast.makeText(MainActivity.this,
                        "Error loading products: " + task.getException().getMessage(),
                        Toast.LENGTH_SHORT).show();
                updateEmptyState(true);
            }
        });
    }

    private void updateEmptyState(boolean isEmpty) {
        View emptyStateView = findViewById(R.id.empty_state);
        if (isEmpty) {
            emptyStateView.setVisibility(View.VISIBLE);
            productsRecyclerView.setVisibility(View.GONE);
        } else {
            emptyStateView.setVisibility(View.GONE);
            productsRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCategoryClick(CategoryItem category) {
        // Load products for selected category
        loadProducts(category.getId());
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
        intent.putExtra("product_id", product.getId());
        startActivity(intent);
    }

    @Override
    public void onAddToCartClick(Product product) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Firebaseutils.addToCart(currentUser.getUid(), product.getId(), 1)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(MainActivity.this,
                                product.getName() + " added to cart",
                                Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(MainActivity.this,
                                "Failed to add to cart: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    });
        } else {
            // User not logged in, prompt to log in
            Toast.makeText(this, "Please log in to add items to cart", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onCategoryClick(Category category, int position) {

    }

    // Helper class for category items
    public static class CategoryItem {
        private String id;
        private String name;
        private int iconResId;

        public CategoryItem(String id, String name, int iconResId) {
            this.id = id;
            this.name = name;
            this.iconResId = iconResId;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getIconResId() {
            return iconResId;
        }
    }
}

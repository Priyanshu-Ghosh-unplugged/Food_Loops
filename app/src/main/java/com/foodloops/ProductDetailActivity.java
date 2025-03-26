package com.foodloops;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.foodloops.R;
import com.foodloops.Product;
import com.foodloops.Store;
import com.foodloops.Firebaseutils;
import com.foodloops.PricingAlgorithm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductDetailActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String productId;
    private Product product;

    // UI components
    private ImageView backButton;
    private ImageView productImage;
    private TextView productName;
    private TextView productDescription;
    private TextView discountedPrice;
    private TextView originalPrice;
    private TextView discountPercentage;
    private TextView expiryTimeRemaining;
    private TextView stockLeft;
    private TextView storeName;
    private TextView storeDescription;
    private TextView storeOpeningHours;
    private TextView storeLocation;
    private Button contactShopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Get product ID from intent
        productId = getIntent().getStringExtra("product_id");
        if (productId == null) {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize UI components
        initializeUI();

        // Load product details
        loadProductDetails();
    }

    private void initializeUI() {
        // Find views
        backButton = findViewById(R.id.back_button);
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.product_description);
        discountedPrice = findViewById(R.id.discounted_price);
        originalPrice = findViewById(R.id.original_price);
        discountPercentage = findViewById(R.id.discount_percentage);
        expiryTimeRemaining = findViewById(R.id.expiry_time_remaining);
        stockLeft = findViewById(R.id.stock_left);
        storeName = findViewById(R.id.store_name);
        storeDescription = findViewById(R.id.store_description);
        storeOpeningHours = findViewById(R.id.store_opening_hours);
        storeLocation = findViewById(R.id.store_location);
        contactShopButton = findViewById(R.id.contact_shop_button);

        // Set click listeners
        backButton.setOnClickListener(v -> finish());

        contactShopButton.setOnClickListener(v -> {
            if (product != null && product.getStore() != null) {
                Store store = product.getStore();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(android.net.Uri.parse("tel:" + store.getPhoneNumber()));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Store information not available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProductDetails() {
        // Show loading indicator
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

        DocumentReference productRef = db.collection("products").document(productId);
        productRef.get().addOnCompleteListener(task -> {
            findViewById(R.id.progress_bar).setVisibility(View.GONE);

            if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                product = task.getResult().toObject(Product.class);
                product.setId(task.getResult().getId());
                displayProductDetails();
            } else {
                Toast.makeText(this, "Error loading product details", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void displayProductDetails() {
        // Set product name and description
        productName.setText(product.getName());
        productDescription.setText(product.getDescription());

        // Load product image
        if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
            Glide.with(this)
                    .load(product.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .into(productImage);
        } else {
            productImage.setImageResource(R.drawable.placeholder_image);
        }

        // Set price information
        discountedPrice.setText(String.format("₹%.2f", product.getCurrentPrice()));
        originalPrice.setText(String.format("₹%.2f", product.getOriginalPrice()));

        // Calculate and display discount percentage
        int discountPercent = product.getDiscountPercentage();
        if (discountPercent > 0) {
            discountPercentage.setText(String.format("%d%% OFF", discountPercent));
            discountPercentage.setVisibility(View.VISIBLE);
            originalPrice.setVisibility(View.VISIBLE);
        } else {
            discountPercentage.setVisibility(View.GONE);
            originalPrice.setVisibility(View.GONE);
        }

        // Set expiry time remaining
        expiryTimeRemaining.setText(PricingAlgorithm.formatTimeRemaining(product.getExpiryDate()));

        // Set stock information
        stockLeft.setText(String.format("%d items", product.getStockRemaining()));

        // Set store information if available
        Store store = product.getStore();
        if (store != null) {
            storeName.setText(store.getName());
            storeDescription.setText(store.getDescription());
            storeOpeningHours.setText(store.getOpeningHours());
            storeLocation.setText(store.getAddress());
        } else {
            // Hide shop details section if store information is not available
            findViewById(R.id.shop_details_section).setVisibility(View.GONE);
        }
    }
}
